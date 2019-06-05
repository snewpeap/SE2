package edu.nju.cinemasystem.blservices.impl.sale;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.nju.cinemasystem.blservices.sale.ticket.Ticket;
import edu.nju.cinemasystem.blservices.sale.ticket.Manage.RefundTicketManage;
import edu.nju.cinemasystem.controller.sale.TicketController;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.Form.RefundStrategyForm;

@Service
public class TicketImpl implements Ticket, RefundTicketManage {

    @Autowired
    TicketController TicketsMapper;
    
    @Override
    public Response lockSeat(List<Integer> seatID, int userID, int arrangementID){
        
        return null;
    }

    @Override
    public Response payOrder(long orderID, int userID, int couponID){
        return null;
    }
    
    @Override
    public Response payOrderByVIPCard(long orderID, int userID, int couponID){
        return null;
    }


    @Override
    public Response getTicketPurchaseRecordByUser(int userID){
        return null;
    }

    @Override
    public Response cancelOrder(int userID, long orderID){
        return null;
    }

    @Override
    public Response refundTicket(int userID, int ticketID){
        return null;
    }

    @Override
    public Response addRefundTicketManage(RefundStrategyForm refundStrategyForm){
        return null;
    }
    

    @Override
    public Response modifyRefundTicketManage(RefundStrategyForm refundStrategyForm){
        return null;
    }

    @Override
    public Response getOrderByScheduleIdAndUserId(int userId, int scheduleId){
        return null;
    }
}