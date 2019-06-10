package edu.nju.cinemasystem.web.controller.sale;

import edu.nju.cinemasystem.blservices.sale.ticket.RefundTicketManage;
import edu.nju.cinemasystem.blservices.sale.ticket.Ticket;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.RefundStrategyForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class TicketController {
    @Autowired
    private Ticket ticket;
    @Autowired
    private RefundTicketManage refundTicketManage;

    @PostMapping("/user/ticket/lockSeats/{arrangementId}")
    public Response lockSeats(@RequestBody List<Integer> seatId, HttpSession session, @PathVariable int arrangementId){
        int userId = Integer.parseInt(String.valueOf(session.getAttribute("id")));
        return ticket.lockSeat(seatId,userId,arrangementId);
    }

    @PostMapping("/user/ticket/pay")
    public Response payOrder(@RequestBody long orderId,HttpSession session,@RequestParam int couponId){
        int userId = Integer.parseInt(String.valueOf(session.getAttribute("id")));
        return ticket.payOrder(orderId,userId,couponId);
    }

    @PostMapping("/user/ticket/payByVIP")
    public Response payOrderByVIP(@RequestBody long orderId,HttpSession session,@RequestParam int couponId){
        int userId = Integer.parseInt(String.valueOf(session.getAttribute("id")));
        return ticket.payOrderByVIPCard(orderId,userId,couponId);
    }

    @GetMapping("/user/ticket/purchaseRecord")
    public Response getPurchaseRecord(HttpSession session){
        int userId = Integer.parseInt(String.valueOf(session.getAttribute("id")));
        return ticket.getHistoricalConsumptionsByUserId(userId);
    }

    @PostMapping("/user/ticket/cancel")
    public Response cancelOrder(@RequestBody long orderId,HttpSession session){
        int userId = Integer.parseInt(String.valueOf(session.getAttribute("id")));
        return ticket.cancelOrder(userId,orderId);
    }

    @PostMapping("/user/ticket/refund")
    public Response refundTicket(HttpSession session,@RequestBody int ticketId){
        int userId = Integer.parseInt(String.valueOf(session.getAttribute("id")));
        return ticket.refundTicket(userId,ticketId);
    }

    @GetMapping("/user/ticket/get")
    public Response getAllTickets(HttpSession session){
        int userId = Integer.parseInt(String.valueOf(session.getAttribute("id")));
        return ticket.getAllTicketsByUserId(userId);
    }

    @GetMapping("/user/ticket/get/existing")
    public Response getExistingTickets(@PathVariable int scheduleId,HttpSession session){
        int userId = Integer.parseInt(String.valueOf(session.getAttribute("id")));
        return ticket.getOrderByScheduleIdAndUserId(userId,scheduleId);
    }

    @PostMapping("/admin/ticket/refundStrategy/add")
    public Response addRefundStrategy(@RequestBody RefundStrategyForm refundStrategyForm){
        return refundTicketManage.addRefundTicketManage(refundStrategyForm);
    }

    @PostMapping("/admin/ticket/refundStrategy/modify")
    public Response modifyRefundStrategy(@RequestBody RefundStrategyForm refundStrategyForm){
        return refundTicketManage.modifyRefundTicketManage(refundStrategyForm);
    }
}
