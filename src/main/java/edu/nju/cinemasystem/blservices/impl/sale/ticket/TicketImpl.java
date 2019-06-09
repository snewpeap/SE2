package edu.nju.cinemasystem.blservices.impl.sale.ticket;

import edu.nju.cinemasystem.blservices.cinema.arrangement.Arrangement;
import edu.nju.cinemasystem.blservices.cinema.hall.HallManage;
import edu.nju.cinemasystem.blservices.sale.promotion.Coupon;
import edu.nju.cinemasystem.blservices.vip.SalesInfo;
import edu.nju.cinemasystem.blservices.vip.VIPCard;
import edu.nju.cinemasystem.data.po.Order;
import edu.nju.cinemasystem.data.po.RefundStrategy;
import edu.nju.cinemasystem.data.po.Ticket;
import edu.nju.cinemasystem.data.vo.*;
import edu.nju.cinemasystem.dataservices.sale.OrderMapper;
import edu.nju.cinemasystem.dataservices.sale.ticket.RefundStrategyMapper;
import edu.nju.cinemasystem.dataservices.sale.ticket.TicketsMapper;
import edu.nju.cinemasystem.util.properties.message.ArrangementMsg;
import edu.nju.cinemasystem.util.properties.message.GlobalMsg;
import edu.nju.cinemasystem.util.properties.message.TicketMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Executors;

@Service
public class TicketImpl implements
        edu.nju.cinemasystem.blservices.sale.ticket.Ticket,
        SalesInfo {

    private final TicketsMapper ticketsMapper;
    private final //这个是业务逻辑层的接口
            Arrangement arrangement;
    private final //这个是业务逻辑层的接口
            Coupon coupon;
    private final
    GlobalMsg globalMsg;
    private final
    ArrangementMsg arrangementMsg;
    private final //这个是业务逻辑层的接口
            VIPCard vipCard;
    private final
    TicketMsg ticketMsg;
    private final
    OrderMapper orderMapper;
    private final
    RefundStrategyMapper refundStrategyMapper;
    private final
    HallManage hallManage;

    private static DelayQueue<DelayedTask> delayQueue = new DelayQueue<>();

    @Autowired
    public TicketImpl(TicketsMapper ticketsMapper, Arrangement arrangement, Coupon coupon, GlobalMsg globalMsg, ArrangementMsg arrangementMsg, VIPCard vipCard, TicketMsg ticketMsg, OrderMapper orderMapper, RefundStrategyMapper refundStrategyMapper, HallManage hallManage) {
        this.ticketsMapper = ticketsMapper;
        this.arrangement = arrangement;
        this.coupon = coupon;
        this.globalMsg = globalMsg;
        this.arrangementMsg = arrangementMsg;
        this.vipCard = vipCard;
        this.ticketMsg = ticketMsg;
        this.orderMapper = orderMapper;
        this.refundStrategyMapper = refundStrategyMapper;
        this.hallManage = hallManage;
    }

    /**
     * 通过延时队列处理超时订单
     */
    @PostConstruct
    public void processTimeoutOrdersByDelayQueue() {
        Executors.newSingleThreadExecutor().execute(() -> {
            DelayedTask delayedTask;
            while (true) {
                try {
                    delayedTask = delayQueue.take();
                    makeOrderInvalid(delayedTask);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public Response lockSeat(List<Integer> seatIDs, int userID, int arrangementID) {
        Response response;
        if (arrangement.isArrangementStart(arrangementID)) {
            response = Response.fail();
            response.setMessage(arrangementMsg.getArrangementStart());
            return response;
        }
        Date date = new Date();
        float realAmount = arrangement.getFareByID(arrangementID);
        float totalAmount = realAmount * seatIDs.size();
        long orderID = Long.parseLong(String.valueOf(date) + String.valueOf((long) (1 + Math.random() * (100))));
        Order order = new Order(orderID, totalAmount, totalAmount, date, (byte) 2);

        orderMapper.insert(order);
        List<Ticket> tickets = new ArrayList<>();
        for (int seatID : seatIDs) {
            Ticket ticket = new Ticket(userID, arrangementID, seatID, date, (byte) 0, realAmount, orderID);
            ticketsMapper.insertSelective(ticket);
            arrangement.changeArrangementSeatStatus(arrangementID, seatID, (byte) 1);
            tickets.add(ticket);
        }
        OrderWithCouponVO orderWithCouponVO = assembleOrderWithCouponVO(tickets, userID, orderID, totalAmount);
        DelayedTask delayedTask = new DelayedTask(orderID, date, tickets);
        delayQueue.add(delayedTask);
        response = Response.success();
        response.setContent(orderWithCouponVO);

        return response;
    }

    @Override
    public Response payOrder(long orderID, int userID, int couponID) {
        Response response;
        float couponAmount = coupon.getCouponAmountByID(couponID);
        Order order = orderMapper.selectByPrimaryKey(orderID);
        coupon.removeCouponByID(couponID);
        List<Ticket> tickets = completeTicket(orderID, couponAmount);
        order.setRealAmount(order.getOriginalAmount() - couponAmount);
        order.setDate(new Date());
        order.setUseVipcard((byte) 0);
        orderMapper.updateByPrimaryKeySelective(order);
        int movieID = arrangement.getMovieIDbyID(tickets.get(0).getArrangementId());
        coupon.sendCouponsToUser(userID, movieID);
        response = Response.success();
        response.setMessage(globalMsg.getOperationSuccess());
        return response;
    }

    @Override
    public Response payOrderByVIPCard(long orderID, int userID, int couponID) {
        Response response;
        float couponAmount = coupon.getCouponAmountByID(couponID);
        List<Ticket> tickets = ticketsMapper.selectByOrderID(orderID);
        int arrangementID = tickets.get(0).getArrangementId();
        int movieID = arrangement.getMovieIDbyID(arrangementID);
        float totalAmount = arrangement.getFareByID(arrangementID) * tickets.size();
        Order order = orderMapper.selectByPrimaryKey(orderID);
        if (vipCard.reduceVIPBalance(userID, totalAmount - couponAmount)) {
            completeTicket(orderID, couponAmount);
            order.setRealAmount(order.getOriginalAmount() - couponAmount);
            order.setDate(new Date());
            order.setUseVipcard((byte) 1);
            orderMapper.updateByPrimaryKeySelective(order);
            coupon.sendCouponsToUser(userID, movieID);
            response = Response.success();
            response.setMessage(globalMsg.getOperationSuccess());
        } else {
            response = Response.fail();
            response.setMessage(ticketMsg.getBalanceNotEnough());
        }
        return response;
    }

    @Override
    public Response cancelOrder(int userID, long orderID) {
        Response response;
        List<Ticket> tickets = ticketsMapper.selectByOrderID(orderID);
        for (Ticket ticket : tickets) {
            ticketsMapper.deleteByPrimaryKey(ticket.getId());
            int seatID = ticket.getSeatId();
            arrangement.changeArrangementSeatStatus(ticket.getArrangementId(),seatID,(byte)0);
        }
        orderMapper.deleteByPrimaryKey(orderID);
        for (DelayedTask or : delayQueue) {
            if (or.getID() == orderID) {
                delayQueue.remove(or);
            }
        }
        response = Response.success();
        response.setMessage(globalMsg.getOperationSuccess());
        return response;
    }

    @Override
    public Response refundTicket(int userID, int ticketID) {
        Ticket ticket = ticketsMapper.selectByPrimaryKey(ticketID);
        List<RefundStrategy> refundStrategies = refundStrategyMapper.selectAll();
        refundStrategies.sort((RefundStrategy r1, RefundStrategy r2) -> r2.getDay() - r1.getDay());
        Date today = new Date();
        float amount = 0;
        Response response;
        for (RefundStrategy refundStrategy : refundStrategies) {
            if (today.compareTo(addDate(ticket.getDate(), refundStrategy.getDay())) > 0) {
                amount = ticket.getRealAmount() * refundStrategy.getPercentage();
            }
        }
        if (amount == 0) {
            response = Response.fail();
            response.setMessage(ticketMsg.getRefundDisable());
            return response;
        }
        long orderID = ticket.getOrderID();
        Order order = orderMapper.selectByPrimaryKey(orderID);
        if (order.getUseVipcard() == (byte) 1) {
            vipCard.addVIPBalance(ticket.getUserId(), amount);
        }
        ticket.setStatus((byte)3);
        int seatID = ticket.getSeatId();
        arrangement.changeArrangementSeatStatus(ticket.getArrangementId(),seatID,(byte)0);
        ticketsMapper.updateByPrimaryKeySelective(ticket);
        response = Response.success();
        response.setMessage(globalMsg.getOperationSuccess());
        return response;
    }

    @Override
    public Response getOrderByScheduleIdAndUserId(int userId, int scheduleId) {
        List<Ticket> tickets = ticketsMapper.selectByUserID(userId);
        List<Ticket> needTickets = new ArrayList<>();
        long orderID = 0L;
        float totalAmount = 0;
        if (tickets != null) {
            for (Ticket ticket : tickets) {
                if (ticket.getArrangementId() == scheduleId && ticket.getStatus() == (byte) 0) {
                    needTickets.add(ticket);
                }
            }
            orderID = needTickets.get(0).getOrderID();
            totalAmount = needTickets.size() * needTickets.get(0).getRealAmount();
        }
        OrderWithCouponVO orderWithCouponVO = assembleOrderWithCouponVO(needTickets, userId, orderID, totalAmount);
        Response response = Response.success();
        response.setContent(orderWithCouponVO);
        return response;
    }

    @Override
    public Response getAllTicketsByUserId(int userId) {
        List<Ticket> tickets = ticketsMapper.selectByUserID(userId);
        List<TicketVO> ticketVOS = new ArrayList<>();
        if (tickets != null) {
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
        Response response;
        if (tickets == null) {
            response = Response.success();
            response.setContent(null);
            return response;
        }
        Date date = tickets.get(0).getDate();
        List<TicketVO> ticketVOS = new ArrayList<>();
        for (Ticket ticket : tickets) {
            if (!(ticket.getStatus() == (byte) 0 && ticket.getStatus() == (byte) 2)) {
                ticketVOS.add(assembleTicketVO(ticket));
            }
        }
        Map<Long, List<TicketVO>> ticketVOsMapByOrderID = new HashMap<>();
        for (TicketVO ticketVO : ticketVOS) {
            if (!ticketVOsMapByOrderID.containsKey(ticketVO.getOrderID())) {
                ticketVOsMapByOrderID.put(ticketVO.getOrderID(), new ArrayList<>());
            }
            ticketVOsMapByOrderID.get(ticketVO.getOrderID()).add(ticketVO);
        }
        List<OrderVO> orderVOS = new ArrayList<>();
        for (Map.Entry<Long, List<TicketVO>> entry : ticketVOsMapByOrderID.entrySet()) {
            List<TicketVO> oneTicketVOs = entry.getValue();
            float realSpend = oneTicketVOs.size() * oneTicketVOs.get(0).getRealAmount();
            float originalSpend = oneTicketVOs.size() * (arrangement.getFareByID(oneTicketVOs.get(0).getArrangementId()));
            orderVOS.add(new OrderVO(entry.getKey(), oneTicketVOs, realSpend, originalSpend, date));
        }
        response = Response.success();
        response.setContent(orderVOS);
        return response;
    }

    @Override
    public float getConsumption(int userID) {
        List<Ticket> tickets = ticketsMapper.selectByUserID(userID);
        float amount = 0;
        for (Ticket ticket : tickets) {
            if (ticket.getStatus() == (byte) 1) {
                amount += ticket.getRealAmount();
            }
        }
        return amount;
    }

    /**
     * 使订单失效
     *
     * @param delayedTask 延时任务
     */
    private void makeOrderInvalid(DelayedTask delayedTask) {
        long orderID = delayedTask.getID();
        List<Ticket> tickets = ticketsMapper.selectByOrderID(orderID);
        Order order = orderMapper.selectByPrimaryKey(orderID);
        for (Ticket ticket : tickets) {
            ticket.setStatus((byte) 2);
            ticketsMapper.updateByPrimaryKeySelective(ticket);
            arrangement.changeArrangementSeatStatus(ticket.getArrangementId(), ticket.getSeatId(), (byte) 0);
        }
        order.setUseVipcard((byte) 3);
        orderMapper.updateByPrimaryKeySelective(order);
    }

    /**
     * 把订单上的票的状态改为已完成并且将其移出延时队列,并且返回订单里的所有票（为了知道是什么电影和算钱）
     *
     * @param orderID 订单ID
     */
    private List<Ticket> completeTicket(long orderID, float couponAmount) {
        List<Ticket> tickets = ticketsMapper.selectByOrderID(orderID);
        float realAmount = couponAmount / (float) tickets.size();
        for (Ticket ticket : tickets) {
            ticket.setStatus((byte) 1);
            ticket.setDate(new Date());
            ticket.setRealAmount(realAmount);
            ticketsMapper.updateByPrimaryKeySelective(ticket);
        }
        for (DelayedTask or : delayQueue) {
            if (or.getID() == orderID) {
                delayQueue.remove(or);
            }
        }
        return tickets;
    }

    /**
     * 组装一个OrderWithCouponVO
     *
     * @param tickets Ticket列表
     * @param userID 用户ID
     * @param orderID 订单
     * @param totalAmount 订单总金额
     * @return OrderWithCouponVO
     */
    private OrderWithCouponVO assembleOrderWithCouponVO(List<Ticket> tickets, int userID, long orderID, float totalAmount) {
        List<TicketVO> ticketVOs = new ArrayList<>();
        List<CouponVO> couponVOs = coupon.getAvailableCouponsByUserAndTickets(userID, totalAmount);
        if (tickets != null) {
            for (Ticket ticket : tickets) {
                TicketVO ticketVO = assembleTicketVO(ticket);
                ticketVOs.add(ticketVO);
            }
        }
        return new OrderWithCouponVO(orderID, ticketVOs, couponVOs);
    }

    /**
     * 给指定的日期加上天数
     *
     * @param date 指定的日期
     * @param day 天数
     * @return 加上天数后的日期
     */
    private Date addDate(Date date, int day) {
        long time = date.getTime();
        long delayTime = day * 24 * 60 * 60 * 1000L;
        time += delayTime;
        return new Date(time);
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
        Date[] dates = arrangement.getStartDateAndEndDate(ticket.getArrangementId());
        Date startDate = dates[0];
        Date endDate = dates[1];
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
        String hallName = arrangement.getHallNameByArrangementID(ticket.getArrangementId());
        return new TicketVO(id, orderID, userID, arrangementId, startDate, endDate, status, realAmount, row, column, hallName);
    }

}