package edu.nju.cinemasystem.blservices.impl.sale.ticket;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import edu.nju.cinemasystem.blservices.cinema.arrangement.ArrangementService;
import edu.nju.cinemasystem.blservices.cinema.hall.HallManage;
import edu.nju.cinemasystem.blservices.movie.MovieService;
import edu.nju.cinemasystem.blservices.sale.promotion.CouponService;
import edu.nju.cinemasystem.blservices.sale.ticket.TicketService;
import edu.nju.cinemasystem.blservices.vip.SalesInfo;
import edu.nju.cinemasystem.blservices.vip.VIPCardService;
import edu.nju.cinemasystem.data.po.Order;
import edu.nju.cinemasystem.data.po.RefundStrategy;
import edu.nju.cinemasystem.data.po.Ticket;
import edu.nju.cinemasystem.data.vo.*;
import edu.nju.cinemasystem.dataservices.sale.OrderMapper;
import edu.nju.cinemasystem.dataservices.sale.ticket.RefundStrategyMapper;
import edu.nju.cinemasystem.dataservices.sale.ticket.TicketsMapper;
import edu.nju.cinemasystem.util.properties.AlipayProperties;
import edu.nju.cinemasystem.util.properties.message.TicketMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Executors;

@Service
public class TicketImpl
        implements TicketService, SalesInfo {

    private static final Logger LOG = LoggerFactory.getLogger(TicketImpl.class);

    private final ArrangementService arrangementService; //这个是业务逻辑层的接口
    private final CouponService couponService;//这个是业务逻辑层的接口
    private final VIPCardService vipCardService;//这个是业务逻辑层的接口
    private final HallManage hallManage;
    private final MovieService movieService;
    private final OrderMapper orderMapper;
    private final TicketsMapper ticketsMapper;
    private final RefundStrategyMapper refundStrategyMapper;
    private final AlipayProperties alipayProperties;
    private final TicketMsg ticketMsg;
    private OrderHolder orderHolder;

    @Autowired
    public TicketImpl(TicketsMapper ticketsMapper, ArrangementService arrangementService, CouponService couponService, VIPCardService vipCardService, TicketMsg ticketMsg, OrderMapper orderMapper, RefundStrategyMapper refundStrategyMapper, HallManage hallManage, MovieService movieService, AlipayProperties alipayProperties) {
        this.ticketsMapper = ticketsMapper;
        this.arrangementService = arrangementService;
        this.couponService = couponService;
        this.vipCardService = vipCardService;
        this.ticketMsg = ticketMsg;
        this.orderMapper = orderMapper;
        this.refundStrategyMapper = refundStrategyMapper;
        this.hallManage = hallManage;
        this.movieService = movieService;
        this.alipayProperties = alipayProperties;
        orderHolder = new OrderHolder();
    }

    /**
     * 通过延时队列处理超时订单
     */
    @PostConstruct
    public void processTimeoutOrdersByDelayQueue() {
        orderHolder.run();
    }

    @Override
    @Transactional
    public Response lockSeat(List<Integer> seatIDs, int userID, int arrangementID) {

        if (arrangementService.isArrangementStart(arrangementID)) {
            return Response.fail(ticketMsg.getArrangementStart());
        }
        Date date = new Date();
        float realAmount = arrangementService.getFareByID(arrangementID);
        float totalAmount = realAmount * seatIDs.size();
        long orderID = Long.parseLong(date.getTime() + String.valueOf((long) ((Math.random() * 9 + 1) * 100)));

        Order order = Order.assembleOrderPO(orderID, totalAmount, totalAmount, date, (byte) 2, userID);
        orderMapper.insertSelective(order);
        List<Ticket> tickets = new ArrayList<>();
        for (int seatID : seatIDs) {
            if (arrangementService.isSeatBeenLocked(arrangementID, seatID)) {
                orderMapper.deleteByPrimaryKey(orderID);
                return Response.fail(ticketMsg.getSeatBeenLocked());
            }
            arrangementService.changeArrangementSeatStatus(arrangementID, seatID, true);
            Ticket ticket = Ticket.assembleTicketPO(userID, arrangementID, seatID, date, (byte) 0, realAmount, orderID);
            ticketsMapper.insertSelective(ticket);
            tickets.add(ticket);
        }
        orderHolder.addTask(new DelayedTask(orderID, date, tickets, 0));//此时没有优惠券
        OrderWithCouponVO orderWithCouponVO = assembleOrderWithCouponVO(tickets, userID, orderID, totalAmount);
        Response response = Response.success();
        response.setContent(orderWithCouponVO);

        return response;
    }

    @Override
    @Transactional
    public Response payable(long orderID, int couponID, int userID) {
        Order order = orderMapper.selectByPrimaryKey(orderID);
        if (order.getStatus() != 2 || orderMapper.selectByUserAndOrderID(userID, orderID).isEmpty()) {
            return Response.fail(ticketMsg.getOrderInvalid());
        }
        if (couponID > 0) {
            List<CouponVO> coupons = couponService.getAvailableCouponsByUserAndTickets(userID, order.getOriginalAmount());
            CouponVO thecoupon = null;
            for (CouponVO couponVO : coupons) {
                if (couponVO.getID() == couponID) {
                    thecoupon = couponVO;
                    break;
                }
            }
            if (thecoupon != null) {
                order.setRealAmount(order.getOriginalAmount() - thecoupon.getDiscountAmount());
                orderMapper.updateByPrimaryKeySelective(order);
                List<Ticket> tickets = ticketsMapper.selectByOrderID(orderID);
                float original = arrangementService.getFareByID(tickets.get(0).getArrangementId());
                float minus = thecoupon.getDiscountAmount() / (float) tickets.size();
                tickets.forEach(
                        ticket -> {
                            ticket.setRealAmount(original - minus);
                            ticketsMapper.updateByPrimaryKeySelective(ticket);
                        }
                );
                if (orderHolder.setCouponToOrder(orderID, couponID)) {
                    return Response.success();
                } else {
                    return Response.fail(ticketMsg.getOrderInvalid());
                }
            } else {
                return Response.fail(ticketMsg.getCouponInvalid());
            }
        } else {
            return Response.success();
        }
    }

    @Override
    public String requestAlipay(long orderID) throws AlipayApiException {
        Order order = orderMapper.selectByPrimaryKey(orderID);
        AlipayClient alipayClient = new DefaultAlipayClient(
                alipayProperties.getAliGateway(),
                alipayProperties.getAppId(),
                alipayProperties.getMerchantPrivateKey(),
                "json",
                alipayProperties.getCharset(),
                alipayProperties.getAliPublicKey(),
                alipayProperties.getSignType()
        );
        AlipayTradePagePayModel payModel = new AlipayTradePagePayModel();
        payModel.setOutTradeNo(String.valueOf(orderID));
        payModel.setProductCode("FAST_INSTANT_TRADE_PAY");
        payModel.setTotalAmount(new DecimalFormat("0.00").format(order.getRealAmount()));
        payModel.setSubject("电影票订单" + order.getId());
        payModel.setTimeoutExpress("15m");
        AlipayTradePagePayRequest payRequest = new AlipayTradePagePayRequest();
        payRequest.setNotifyUrl(alipayProperties.getNotifyUrl());
        payRequest.setReturnUrl(alipayProperties.getReturnUrl());
        payRequest.setBizModel(payModel);
        return alipayClient.pageExecute(payRequest).getBody();
    }

    @Override
    @Transactional
    public Response payOrder(long orderID, float amount) {
        Order order = orderMapper.selectByPrimaryKey(orderID);
        if (order.getStatus() == 3 || Math.abs(order.getRealAmount() - amount) > 0.01) {
            return Response.fail(ticketMsg.getOrderInvalid());
        }
        int couponID = orderHolder.getCouponID(orderID);
        if (couponID > 0) {
            couponService.removeCouponByID(couponID);
        }
        List<Ticket> tickets = ticketsMapper.selectByOrderID(orderID);
        orderHolder.completeOrder(tickets, order, false);
        int movieID = arrangementService.getMovieIDbyID(tickets.get(0).getArrangementId());
        int userID = order.getUserId();
        couponService.sendCouponsToUser(userID, movieID);
        return Response.success();
    }

    @Override
    @Transactional
    public Response payOrderByVIPCard(long orderID, int userID) {
        Order order = orderMapper.selectByPrimaryKey(orderID);
        Response payResult = vipCardService.pay(userID, order.getRealAmount());
        if (payResult.isSuccess()) {
            List<Ticket> tickets = ticketsMapper.selectByOrderID(orderID);
            int arrangementID = tickets.get(0).getArrangementId();
            int movieID = arrangementService.getMovieIDbyID(arrangementID);
            orderHolder.completeOrder(tickets, order, true);
            couponService.sendCouponsToUser(userID, movieID);
            return Response.success(ticketMsg.getOperationSuccess());
        } else {
            return payResult;
        }
    }

    @Override
    public Response getOrderStatus(long orderID) {
        Order order = orderMapper.selectByPrimaryKey(orderID);
        if (order.getStatus() >= 2) {
            return Response.fail();
        } else {
            return Response.success();
        }
    }

    @Override
    @Transactional
    public Response cancelOrder(int userID, long orderID) {
        Order order = orderMapper.selectByPrimaryKey(orderID);
        if (order.getStatus() != 2 || order.getUserId() != userID) {
            return Response.fail(ticketMsg.getOrderInvalid());
        }
        List<Ticket> tickets = ticketsMapper.selectByOrderID(orderID);
        for (Ticket ticket : tickets) {
            ticketsMapper.deleteByPrimaryKey(ticket.getId());
            arrangementService.changeArrangementSeatStatus(ticket.getArrangementId(), ticket.getSeatId(), false);
        }
        orderMapper.deleteByPrimaryKey(orderID);
        orderHolder.removeTask(orderID);
        return Response.success(ticketMsg.getOperationSuccess());
    }

    @Override
    @Transactional
    public Response refundTicket(int userID, int ticketID) {
        Ticket ticket = ticketsMapper.selectByPrimaryKey(ticketID);
        if (ticket == null) {
            return Response.fail(ticketMsg.getOperationFailed() + " : 票不存在");
        } else if (ticket.getUserId() != userID) {
            return Response.fail(ticketMsg.getOperationFailed() + " : 未持有该电影票");
        }
        Order order = orderMapper.selectByPrimaryKey(ticket.getOrderID());
        if (order.getStatus() > 1) {
            return Response.fail(ticketMsg.getOrderInvalid());
        }
        Date arrangementStartTime = arrangementService.getStartDateAndEndDate(ticket.getArrangementId())[0];
        Date currentTime = new Date();
        if (arrangementStartTime.before(currentTime)) {
            return Response.fail(ticketMsg.getArrangementStart());
        }
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date arrangementStartDate = simpleDateFormat.parse(simpleDateFormat.format(arrangementStartTime));
            Date today = simpleDateFormat.parse(simpleDateFormat.format(currentTime));
            long datToCome = (arrangementStartDate.getTime() - today.getTime()) / (1000 * 3600 * 24);
            RefundStrategy refundStrategy = refundStrategyMapper.selectByDay(datToCome);
            if (!refundStrategy.canRefund()) {
                return Response.fail(ticketMsg.getRefundDisable());
            }
            float refundAmount = ticket.getRealAmount() * refundStrategy.getPercentage() / (float) 100;
            BigDecimal b = new BigDecimal(refundAmount);
            refundAmount = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
            Response refundResponse;
            if (order.getStatus() == 1) {
                refundResponse = vipCardService.addVIPBalance(ticket.getUserId(), refundAmount);
            } else {
                refundResponse = aliRefund(order.getId(), ticketID, refundAmount);
            }
            if (refundResponse.isSuccess()) {
                ticket.setStatus((byte) 3);
                ticketsMapper.updateByPrimaryKeySelective(ticket);
                int seatID = ticket.getSeatId();
                arrangementService.changeArrangementSeatStatus(ticket.getArrangementId(), seatID, false);
                return Response.success();
            } else {
                return refundResponse;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return Response.fail(e.getMessage());
        }
    }

    private Response aliRefund(long orderID, int ticketID, float refundAmount) {
        AlipayClient alipayClient = new DefaultAlipayClient(
                alipayProperties.getAliGateway(),
                alipayProperties.getAppId(),
                alipayProperties.getMerchantPrivateKey(),
                "json",
                alipayProperties.getCharset(),
                alipayProperties.getAliPublicKey(),
                alipayProperties.getSignType()
        );
        AlipayTradeRefundModel refundModel = new AlipayTradeRefundModel();
        refundModel.setOutTradeNo(String.valueOf(orderID));
        refundModel.setOutRequestNo(orderID + String.valueOf(ticketID));
        refundModel.setRefundAmount(new DecimalFormat("0.00").format(refundAmount));
        AlipayTradeRefundRequest refundRequest = new AlipayTradeRefundRequest();
        refundRequest.setBizModel(refundModel);
        AlipayTradeRefundResponse refundResponse;
        try {
            refundResponse = alipayClient.execute(refundRequest);
        } catch (AlipayApiException e) {
            return Response.fail("支付宝异常");
        }
        String fund_change = refundResponse.getFundChange();
        if (fund_change != null && fund_change.equals("Y")) {
            return Response.success();
        } else {
            return Response.fail(refundResponse.getSubMsg());
        }
    }

    @Override
    public Response getOrderByScheduleIdAndUserId(int userId, int scheduleId) {
        List<Ticket> tickets = ticketsMapper.selectByUserID(userId);
        List<Ticket> needTickets = new ArrayList<>();
        long orderID;
        float totalAmount;
        for (Ticket ticket : tickets) {
            if (ticket.getArrangementId() == scheduleId && ticket.getStatus() == (byte) 0) {
                needTickets.add(ticket);
            }
        }
        Response response = Response.success();
        if (needTickets.size() > 0) {
            orderID = needTickets.get(0).getOrderID();
            totalAmount = needTickets.size() * needTickets.get(0).getRealAmount();
            OrderWithCouponVO orderWithCouponVO = assembleOrderWithCouponVO(needTickets, userId, orderID, totalAmount);
            response.setContent(orderWithCouponVO);
        }
        return response;
    }

    @Override
    public Response getAllTicketsByUserId(int userId) {
        List<Ticket> tickets = ticketsMapper.selectByUserID(userId);
        List<TicketVO> ticketVOS = new ArrayList<>();
        if (tickets.size() != 0) {
            for (Ticket ticket : tickets) {
                if (!(ticket.getStatus() == (byte) 0 && (ticket.getStatus() == (byte) 4))) {
                    ticketVOS.add(assembleTicketVO(ticket));
                }
            }
        }
        Response response = Response.success();
        response.setContent(ticketVOS);
        return response;
    }

    @Override
    public Response getHistoricalConsumptionsByUserId(int userID) {
        List<Ticket> tickets = ticketsMapper.selectByUserID(userID);
        if (tickets.isEmpty()) {
            return Response.success();
        }
        List<TicketVO> ticketVOS = new ArrayList<>();
        for (Ticket ticket : tickets) {
            if (ticket.getStatus() != (byte) 2) {
                ticketVOS.add(assembleTicketVO(ticket));
            }
        }
        Map<Long, List<TicketVO>> ticketVOsMapByOrderID = new TreeMap<>();
        for (TicketVO ticketVO : ticketVOS) {
            long orderID = ticketVO.getOrderID();
            if (!ticketVOsMapByOrderID.containsKey(orderID)) {
                ticketVOsMapByOrderID.put(orderID, new ArrayList<>());
            }
            ticketVOsMapByOrderID.get(orderID).add(ticketVO);
        }
        ticketVOsMapByOrderID = ((TreeMap<Long, List<TicketVO>>) ticketVOsMapByOrderID).descendingMap();
        List<OrderVO> orderVOS = new ArrayList<>();
        for (Map.Entry<Long, List<TicketVO>> entry : ticketVOsMapByOrderID.entrySet()) {
            Order order = orderMapper.selectByPrimaryKey(entry.getKey());
            List<TicketVO> oneTicketVOs = entry.getValue();
            int arrangementID = oneTicketVOs.get(0).getArrangementId();
            Date[] dates = arrangementService.getStartDateAndEndDate(arrangementID);
            int movieID = arrangementService.getMovieIDbyID(arrangementID);
            String movieName = movieService.getMovieNameByID(movieID);
            String hallName = arrangementService.getHallNameByArrangementID(arrangementID);
            orderVOS.add(new OrderVO(
                    entry.getKey(),
                    oneTicketVOs,
                    order.getRealAmount(),
                    order.getOriginalAmount(),
                    order.getDate(), dates[0], dates[1],
                    movieName, hallName, movieID
            ));
        }
        Response response = Response.success();
        response.setContent(orderVOS);
        return response;
    }

    @Override
    public float getConsumption(int userID) {
        List<Ticket> tickets = ticketsMapper.selectByUserID(userID);
        float amount = 0;
        BigDecimal amountDecimal = new BigDecimal(amount);
        for (Ticket ticket : tickets) {
            if (ticket.getStatus() == (byte) 1) {
                amountDecimal = amountDecimal.add(new BigDecimal(ticket.getRealAmount()));
            }
        }
        return amountDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * 组装一个OrderWithCouponVO
     *
     * @param tickets     Ticket列表
     * @param userID      用户ID
     * @param orderID     订单
     * @param totalAmount 订单总金额
     * @return OrderWithCouponVO
     */
    private OrderWithCouponVO assembleOrderWithCouponVO(List<Ticket> tickets, int userID, long orderID, float totalAmount) {
        List<TicketVO> ticketVOs = new ArrayList<>();
        List<CouponVO> couponVOs = couponService.getAvailableCouponsByUserAndTickets(userID, totalAmount);
        int arrangementID = tickets.get(0).getArrangementId();
        Date[] dates = arrangementService.getStartDateAndEndDate(arrangementID);
        String movieName = movieService.getMovieNameByID(arrangementService.getMovieIDbyID(arrangementID));
        String hallName = arrangementService.getHallNameByArrangementID(arrangementID);
        for (Ticket ticket : tickets) {
            TicketVO ticketVO = assembleTicketVO(ticket);
            ticketVOs.add(ticketVO);
        }
        return new OrderWithCouponVO(orderID, ticketVOs, couponVOs, dates[0], dates[1], movieName, hallName);
    }

    /**
     * 封装一个ticketVO
     *
     * @param ticket TicketPO
     * @return TicketVO
     */
    private TicketVO assembleTicketVO(Ticket ticket) {
        int id = ticket.getId();
        long orderID = ticket.getOrderID();
        int userID = ticket.getUserId();
        int arrangementId = ticket.getArrangementId();
        String status = null;
        switch (ticket.getStatus()) {
            case (byte) 0:
                status = "未完成";
                break;
            case (byte) 1:
                status = "已完成";
                break;
            case (byte) 2:
                status = "已失效";
                break;
            case (byte) 3:
                status = "已退票";
                break;
            default:
                break;
        }
        Float realAmount = ticket.getRealAmount();
        int[] seats = hallManage.getSeatBySeatID(ticket.getSeatId());
        int row = seats[0];
        int column = seats[1];
        Date date = ticket.getDate();
        return new TicketVO(id, orderID, userID, arrangementId, status, realAmount, row, column, date);
    }


    private class OrderHolder implements Runnable {

        DelayQueue<DelayedTask> delayQueue = new DelayQueue<>();

        @Override
        public void run() {
            Executors.newSingleThreadExecutor().execute(() -> {
                DelayedTask delayedTask;
                while (true) {
                    try {
                        delayedTask = delayQueue.take();
                        invalidateOrder(delayedTask);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        void addTask(DelayedTask delayedTask) {
            delayQueue.add(delayedTask);
        }

        /**
         * 使订单失效
         *
         * @param delayedTask 延时任务
         */
        @Transactional
        void invalidateOrder(DelayedTask delayedTask) {
            long orderID = delayedTask.getID();
            Order order = orderMapper.selectByPrimaryKey(orderID);
            if (order.getStatus() == 2) {
                List<Ticket> tickets = ticketsMapper.selectByOrderID(orderID);
                for (Ticket ticket : tickets) {
                    ticket.setStatus((byte) 2);
                    ticketsMapper.updateByPrimaryKeySelective(ticket);
                    arrangementService.changeArrangementSeatStatus(ticket.getArrangementId(), ticket.getSeatId(), false);
                }
                order.setStatus((byte) 3);
                orderMapper.updateByPrimaryKeySelective(order);
                LOG.info("将订单 " + orderID + " 失效");
            } else {
                LOG.info("订单 " + orderID + " 在失效前已完成");
            }
        }

        void removeTask(long orderID) {
            for (DelayedTask task : delayQueue) {
                if (task.getID() == orderID) {
                    delayQueue.remove(task);
                }
            }
        }

        /**
         * 把订单上的票的状态改为已完成并且将其移出延时队列,并且返回订单里的所有票（为了知道是什么电影和算钱）
         *
         * @param tickets 票的list
         * @param order   订单
         */
        @Transactional
        void completeOrder(List<Ticket> tickets, Order order, boolean useVIP) {
            Date now = new Date();
            for (Ticket ticket : tickets) {
                ticket.setStatus((byte) 1);
                ticket.setDate(now);
                ticketsMapper.updateByPrimaryKeySelective(ticket);
            }
            order.setDate(now);
            order.setStatus(useVIP ? (byte) 1 : (byte) 0);
            orderMapper.updateByPrimaryKeySelective(order);
            removeTask(order.getId());
        }

        boolean setCouponToOrder(long orderID, int couponID) {
            boolean set = false;
            for (DelayedTask task : delayQueue) {
                if (task.getID() == orderID) {
                    task.setCouponID(couponID);
                    set = true;
                }
            }
            return set;
        }

        int getCouponID(long orderID) {
            int couponID = -1;
            for (DelayedTask task : delayQueue) {
                if (task.getID() == orderID) {
                    couponID = task.getCouponID();
                }
            }
            return couponID;
        }
    }
}