package edu.nju.cinemasystem.blservices.impl.sale.ticket;

import edu.nju.cinemasystem.blservices.cinema.arrangement.Arrangement;
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

    @Autowired
    TicketsMapper ticketsMapper;
    @Autowired //这个是业务逻辑层的接口
    Arrangement arrangement;
    @Autowired //这个是业务逻辑层的接口
    Coupon coupon;
    @Autowired
    GlobalMsg globalMsg;
    @Autowired
    ArrangementMsg arrangementMsg;
    @Autowired //这个是业务逻辑层的接口
    VIPCard vipCard;
    @Autowired
    TicketMsg ticketMsg;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    RefundStrategyMapper refundStrategyMapper;

    private static DelayQueue<OrderMessage> delayQueue =  new DelayQueue<OrderMessage>();

    /**
     * 通过延时队列处理超时订单
     */
    @PostConstruct
    public void processTimeoutOrdersByDelayQueue(){
        Executors.newSingleThreadExecutor().execute(() -> {
            OrderMessage orderMessage;
            while(true){
                try {
                   orderMessage = delayQueue.take();
                    makeOrderInvalid(orderMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public Response lockSeat(List<Integer> seatIDs, int userID, int arrangementID) {
        Response response;
        if(arrangement.isArrangementStart(arrangementID)){
            response = Response.fail();
            response.setMessage(arrangementMsg.getArrangementStart());
            return response;
        }
        Date date = new Date();
        float realAmount = arrangement.getFareByID(arrangementID);
        float totalAmount = realAmount *seatIDs.size();
        long orderID = Long.parseLong(String.valueOf(date) + String.valueOf((long)(1+Math.random()*(100))));
        Order order = new Order(orderID,totalAmount,totalAmount,date,(byte)2);
        try {
            orderMapper.insert(order);
            List<Ticket> tickets = new ArrayList<Ticket>();
            for(int seatID: seatIDs){
                Ticket ticket = new Ticket(userID, arrangementID, seatID, date, (byte)0, realAmount, orderID);
                ticketsMapper.insertSelective(ticket);
                arrangement.changeArrangementSeatStatus(arrangementID,seatID,(byte)1);
                tickets.add(ticket);
            }
            OrderWithCouponVO orderWithCouponVO = assembleOrderWithCouponVO(tickets,userID,orderID,totalAmount);
            OrderMessage orderMessage = new OrderMessage(orderID, date, tickets);
            delayQueue.add(orderMessage);
            response = Response.success();
            response.setContent(orderWithCouponVO);
        } catch (Exception e) {
            e.printStackTrace();
            response = Response.fail();
            response.setMessage(globalMsg.getWrongParam());
        }
        return response;
    }

    @Override
    public Response payOrder(long orderID, int userID, int couponID) {
        Response response;
        float couponAmount = coupon.getCouponAmountByID(couponID);
        Order order = orderMapper.selectByPrimaryKey(orderID);
        try {
            coupon.removeCouponByID(couponID);
            List<Ticket> tickets = completeTicket(orderID, couponAmount);
            order.setRealAmount(order.getOriginalAmount()-couponAmount);
            order.setDate(new Date());
            order.setUseVipcard((byte)0);
            orderMapper.updateByPrimaryKeySelective(order);
            int movieID = arrangement.getMovieIDbyID(tickets.get(0).getArrangementId());
            coupon.sendCouponsToUser(userID,movieID);
            response = Response.success();
            response.setMessage(globalMsg.getOperationSuccess());
        }catch (Exception e){
            e.printStackTrace();
            response = Response.fail();
            response.setMessage(globalMsg.getOperationFailed());
        }
        return response;
    }

    @Override
    public Response payOrderByVIPCard(long orderID, int userID, int couponID) {
        Response response;
        float couponAmount = coupon.getCouponAmountByID(couponID);
        try {
            List<Ticket> tickets = ticketsMapper.selectByOrderID(orderID);
            int arrangementID = tickets.get(0).getArrangementId();
            int movieID = arrangement.getMovieIDbyID(arrangementID);
            float totalAmount = arrangement.getFareByID(arrangementID) * tickets.size();
            Order order = orderMapper.selectByPrimaryKey(orderID);
            if(vipCard.reduceVIPBalance(userID,totalAmount-couponAmount)){
                completeTicket(orderID, couponAmount);
                order.setRealAmount(order.getOriginalAmount()-couponAmount);
                order.setDate(new Date());
                order.setUseVipcard((byte)1);
                orderMapper.updateByPrimaryKeySelective(order);
                coupon.sendCouponsToUser(userID,movieID);
                response = Response.success();
                response.setMessage(globalMsg.getOperationSuccess());
            }else{
                response = Response.fail();
                response.setMessage(ticketMsg.getBalanceNotEnough());
            }
        }catch (Exception e){
            e.printStackTrace();
            response = Response.fail();
            response.setMessage(globalMsg.getOperationFailed());
        }
        return response;
    }

    @Override
    public Response cancelOrder(int userID, long orderID) {
        Response response;
        List<Ticket> tickets = ticketsMapper.selectByOrderID(orderID);
        try {
            for(Ticket ticket:tickets){
                ticketsMapper.deleteByPrimaryKey(ticket.getId());
            }
            orderMapper.deleteByPrimaryKey(orderID);
            for(Iterator<OrderMessage> it = delayQueue.iterator();it.hasNext();){
                OrderMessage or = it.next();
                if(or.getID() == orderID){
                    delayQueue.remove(or);
                }
            }
            response = Response.success();
            response.setMessage(globalMsg.getOperationSuccess());
        }catch (Exception e){
            e.printStackTrace();
            response = Response.fail();
            response.setMessage(globalMsg.getOperationFailed());
        }
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
        for(RefundStrategy refundStrategy:refundStrategies){
            if(today.compareTo(addDate(ticket.getDate(),refundStrategy.getDay())) > 0){
                amount = ticket.getRealAmount() * refundStrategy.getPercentage();
            }
        }
        if(amount == 0){
            response = Response.fail();
            response.setMessage(ticketMsg.getRefundDisable());
            return response;
        }
        try {
            long orderID = ticket.getOrderID();
            Order order = orderMapper.selectByPrimaryKey(orderID);
            if(order.getUseVipcard() == (byte)1){
                vipCard.addVIPBalance(ticket.getUserId(),amount);
            }
            response = Response.success();
            response.setMessage(globalMsg.getOperationSuccess());
            return response;
        }catch (Exception e){
            e.printStackTrace();
            response = Response.fail();
            response.setMessage(globalMsg.getOperationFailed());
            return response;
        }
    }

    @Override
    public Response getOrderByScheduleIdAndUserId(int userId, int scheduleId) {
        List<Ticket> tickets = ticketsMapper.selectByUserID(userId);
        List<Ticket> needTickets = new ArrayList<Ticket>();
        long orderID = 0L;
        float totalAmount = 0;
        if(tickets != null) {
            for (Ticket ticket : tickets) {
                if (ticket.getArrangementId() == scheduleId && ticket.getStatus() == (byte) 0) {
                    needTickets.add(ticket);
                }
            }
            orderID = needTickets.get(0).getOrderID();
            totalAmount  = needTickets.size() * needTickets.get(0).getRealAmount();
        }
        OrderWithCouponVO orderWithCouponVO = assembleOrderWithCouponVO(needTickets,userId,orderID,totalAmount);
        Response response = Response.success();
        response.setContent(orderWithCouponVO);
        return response;
    }

    @Override
    public Response getAllTicketsByUserId(int userId) {
        List<Ticket> tickets = ticketsMapper.selectByUserID(userId);
        List<TicketVO> ticketVOS = new ArrayList<TicketVO>();
        if(tickets != null){
            for(Ticket ticket : tickets){
                if (!(ticket.getStatus()==(byte)0 && (ticket.getStatus() == (byte)4))){
                    ticketVOS.add(new TicketVO(ticket));
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
        List<TicketVO> ticketVOS = new ArrayList<TicketVO>();
        for (Ticket ticket:tickets){
            if (!(ticket.getStatus() == (byte)0 && ticket.getStatus() == (byte)2 )){
                ticketVOS.add(new TicketVO(ticket));
            }
        }
        Map<Long, List<TicketVO>> ticketVOsMapByOrderID = new HashMap<Long, List<TicketVO>>();
        for(TicketVO ticketVO:ticketVOS){
            if(!ticketVOsMapByOrderID.containsKey(ticketVO.getOrderID())){
                ticketVOsMapByOrderID.put(ticketVO.getOrderID(),new ArrayList<TicketVO>());
            }
            ticketVOsMapByOrderID.get(ticketVO.getOrderID()).add(ticketVO);
        }
        List<TicketPurchaseRecord> ticketPurchaseRecords = new ArrayList<TicketPurchaseRecord>();
        for(Map.Entry<Long,List<TicketVO>> entry:ticketVOsMapByOrderID.entrySet()){
            List<TicketVO> oneTicketVOs = entry.getValue();
            float realSpend = oneTicketVOs.size() * oneTicketVOs.get(0).getRealAmount();
            float originalSpend = oneTicketVOs.size() * (arrangement.getFareByID(oneTicketVOs.get(0).getArrangementId()));
            ticketPurchaseRecords.add(new TicketPurchaseRecord(entry.getKey(),oneTicketVOs,realSpend,originalSpend));
        }
        Response response = Response.success();
        response.setContent(ticketPurchaseRecords);
        return response;
    }

    @Override
    public float getConsumption(int userID) {
        //TODO
        return 0;
    }

    /**
     * 使订单失效
     * @param orderMessage
     */
    private void makeOrderInvalid(OrderMessage orderMessage){
        long orderID = orderMessage.getID();
        List<Ticket> tickets = ticketsMapper.selectByOrderID(orderID);
        Order order = orderMapper.selectByPrimaryKey(orderID);
        try {
            for (Ticket ticket : tickets) {
                ticket.setStatus((byte) 2);
                ticketsMapper.updateByPrimaryKeySelective(ticket);
                arrangement.changeArrangementSeatStatus(ticket.getArrangementId(),ticket.getSeatId(),(byte)0);
            }
            order.setUseVipcard((byte)3);
            orderMapper.updateByPrimaryKeySelective(order);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 把订单上的票的状态改为已完成并且将其移出延时队列,并且返回订单里的所有票（为了知道是什么电影和算钱）
     * @param orderID
     */
    private  List<Ticket> completeTicket(long orderID, float couponAmount){
        List<Ticket> tickets = ticketsMapper.selectByOrderID(orderID);
        try {
            float realAmount = couponAmount / (float)tickets.size();
            for (Ticket ticket : tickets) {
                ticket.setStatus((byte) 1);
                ticket.setDate(new Date());
                ticket.setRealAmount(realAmount);
                ticketsMapper.updateByPrimaryKeySelective(ticket);
            }
            for(Iterator<OrderMessage> it = delayQueue.iterator();it.hasNext();){
                OrderMessage or = it.next();
                if(or.getID() == orderID){
                    delayQueue.remove(or);
                }
            }
            return tickets;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 组装一个OrderWithCouponVO
     * @param tickets
     * @param userID
     * @param orderID
     * @param totalAmount
     * @return OrderWithCouponVO
     */
    private OrderWithCouponVO assembleOrderWithCouponVO(List<Ticket> tickets, int userID, long orderID, float totalAmount){
        List<TicketVO> ticketVOs = new ArrayList<>();
        List<CouponVO> couponVOs = coupon.getAvailableCouponsByUserAndTickets(userID, totalAmount);
        if(tickets!=null) {
            for (Ticket ticket : tickets) {
                ticketVOs.add(new TicketVO(ticket));
            }
        }
        OrderWithCouponVO orderWithCouponVO = new OrderWithCouponVO(orderID, ticketVOs, couponVOs);
        return orderWithCouponVO;
    }

    /**
     * 给指定的日期加上天数
     * @param date
     * @param day
     * @return
     */
    private Date addDate(Date date, int day) {
        long time = date.getTime();
        long delayTime = day *24*60*60*1000L;
        time += delayTime;
        return new Date(time);
    }

}