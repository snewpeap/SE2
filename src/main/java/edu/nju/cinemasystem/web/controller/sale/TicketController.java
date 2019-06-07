package edu.nju.cinemasystem.web.controller.sale;

import edu.nju.cinemasystem.blservices.sale.ticket.Manage.RefundTicketManage;
import edu.nju.cinemasystem.blservices.sale.ticket.Ticket;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.RefundStrategyForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    private Ticket ticket;
    @Autowired
    private RefundTicketManage refundTicketManage;

    @PostMapping("/lockSeats/{arrangementId}")
    public Response lockSeats(@RequestBody List<Integer> seatId, @RequestParam int userId, @PathVariable int arrangementId){
        return ticket.lockSeat(seatId,userId,arrangementId);
    }

    @PostMapping("/pay")
    public Response payOrder(@RequestBody long orderId,@RequestParam int userId,@RequestParam int couponId){
        return ticket.payOrder(orderId,userId,couponId);
    }

    @PostMapping("/payByVIP")
    public Response payOrderByVIP(@RequestBody long orderId,@RequestParam int userId,@RequestParam int couponId){
        return ticket.payOrderByVIPCard(orderId,userId,couponId);
    }

    @GetMapping("/purchaseRecord")
    public Response getPurchaseRecord(@RequestParam int userId){
        return ticket.getTicketPurchaseRecordByUser(userId);
    }

    @PostMapping("/cancel")
    public Response cancelOrder(@RequestBody long orderId,@RequestParam int userId){
        return ticket.cancelOrder(userId,orderId);
    }

    @PostMapping("/refund")
    public Response refundTicket(@RequestParam int userId,@RequestBody int ticketId){
        return ticket.refundTicket(userId,ticketId);
    }

    @PostMapping("/refundStrategy/add")
    public Response addRefundStrategy(@RequestBody RefundStrategyForm refundStrategyForm){
        return refundTicketManage.addRefundTicketManage(refundStrategyForm);
    }

    @PostMapping("/refundStrategy/modify")
    public Response modifyRefundStrategy(@RequestBody RefundStrategyForm refundStrategyForm){
        return refundTicketManage.modifyRefundTicketManage(refundStrategyForm);
    }
}
