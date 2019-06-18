package edu.nju.cinemasystem.web.controller.sale;

import edu.nju.cinemasystem.blservices.sale.ticket.RefundTicketManage;
import edu.nju.cinemasystem.blservices.sale.ticket.TicketService;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.RefundStrategyForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class TicketController {
    @Autowired
    private TicketService ticketService;
    @Autowired
    private RefundTicketManage refundTicketManage;

    @PostMapping("/user/ticket/lockSeats/{arrangementId}")
    public Response lockSeats(@RequestBody List<Integer> seatId, HttpSession session, @PathVariable int arrangementId) {
        int userId = (Integer) session.getAttribute("id");
        return ticketService.lockSeat(seatId, userId, arrangementId);
    }

    @PostMapping("/user/vip/pay/{orderID}")
    public Response payOrderByVIP(@PathVariable long orderID, HttpSession session, @RequestBody int couponID) {
        int userID = (Integer) session.getAttribute("id");
        Response response = ticketService.payable(orderID, couponID, userID);
        if (response.isSuccess()) {
            return ticketService.payOrderByVIPCard(orderID, userID);
        } else {
            return response;
        }
    }

    @GetMapping("/user/purchaseRecord")
    public Response getPurchaseRecord(HttpSession session) {
        int userId = (Integer) session.getAttribute("id");
        return ticketService.getHistoricalConsumptionsByUserId(userId);
    }

    @PostMapping("/user/ticket/cancel")
    public Response cancelOrder(@RequestBody long orderId, HttpSession session) {
        int userId = (Integer) session.getAttribute("id");
        return ticketService.cancelOrder(userId, orderId);
    }

    @PostMapping("/user/ticket/refund")
    public Response refundTicket(HttpSession session, @RequestBody int ticketId) {
        int userId = (Integer) session.getAttribute("id");
        return ticketService.refundTicket(userId, ticketId);
    }

    @GetMapping("/user/ticket/get/existing")
    public Response getExistingTickets(@RequestParam int scheduleId, HttpSession session) {
        int userId = (Integer) session.getAttribute("id");
        return ticketService.getOrderByScheduleIdAndUserId(userId, scheduleId);
    }

    @GetMapping("/user/refundStrategy")
    public Response getUserRefundStrategy(){
        return refundTicketManage.getAllRefunds();
    }

    @GetMapping("/admin/refund/get")
    public Response getRefundStrategy(){
        return refundTicketManage.getAllRefunds();
    }

    @PostMapping("/admin/refund/add")
    public Response addRefundStrategy(@RequestBody RefundStrategyForm refundStrategyForm) {
        return refundTicketManage.addRefundTicketManage(refundStrategyForm);
    }

    @PostMapping("/admin/refund/modify")
    public Response modifyRefundStrategy(@RequestBody RefundStrategyForm refundStrategyForm) {
        return refundTicketManage.modifyRefundTicketManage(refundStrategyForm);
    }


}
