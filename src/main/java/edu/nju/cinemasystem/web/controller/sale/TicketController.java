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

    /**
     * 所作并生成订单，返回订单和可用优惠券列表到展示层
     * @param seatId 座位id列表
     * @param session session
     * @param arrangementId 排片id
     * @return 订单及可用优惠券列表VO
     */
    @PostMapping("/user/ticket/lockSeats/{arrangementId}")
    public Response lockSeats(@RequestBody List<Integer> seatId, HttpSession session, @PathVariable int arrangementId) {
        int userId = (Integer) session.getAttribute("id");
        return ticketService.lockSeat(seatId, userId, arrangementId);
    }

    /**
     * 使用会员卡余额支付订单
     * @param orderID 订单id
     * @param session session
     * @param couponID 使用的优惠券id
     * @return 支付结果
     */
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

    /**
     * 消费记录获取
     * @param session session
     * @return 所有消费记录
     */
    @GetMapping("/user/purchaseRecord")
    public Response getPurchaseRecord(HttpSession session) {
        int userId = (Integer) session.getAttribute("id");
        return ticketService.getHistoricalConsumptionsByUserId(userId);
    }

    /**
     * 取消订单
     * @param orderId 订单id
     * @param session session
     * @return 取消订单的结果
     */
    @PostMapping("/user/ticket/cancel")
    public Response cancelOrder(@RequestBody long orderId, HttpSession session) {
        int userId = (Integer) session.getAttribute("id");
        return ticketService.cancelOrder(userId, orderId);
    }

    /**
     * 退票的统一接口
     * @param session session
     * @param ticketId 要退的票id
     * @return 退票的结果
     */
    @PostMapping("/user/ticket/refund")
    public Response refundTicket(HttpSession session, @RequestBody int ticketId) {
        int userId = (Integer) session.getAttribute("id");
        return ticketService.refundTicket(userId, ticketId);
    }

    /**
     * 检查观众在该排片有没有订单的结果
     * @param scheduleId 排片id
     * @param session session
     * @return 查询有没有订单的结果
     */
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
