package edu.nju.cinemasystem.web.controller.sale;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import edu.nju.cinemasystem.blservices.sale.ticket.TicketService;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.util.properties.AlipayProperties;
import edu.nju.cinemasystem.web.config.CustomWebSecurityConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AlipayController {
    private final TicketService ticketService;
    private final AlipayProperties alipayProperties;

    @Autowired
    public AlipayController(TicketService ticketService, AlipayProperties alipayProperties) {
        this.ticketService = ticketService;
        this.alipayProperties = alipayProperties;
    }

    @RequestMapping("/user/alipay/pay/{orderID}")
    public @ResponseBody Response alipay(@PathVariable long orderID, @RequestBody int couponID, HttpSession session, HttpServletResponse servletResponse) throws AlipayApiException {
        int userID = (Integer) session.getAttribute(CustomWebSecurityConfiguration.KEY_ID);
        Response response = ticketService.payable(orderID, couponID, userID);
        if (response.isSuccess()) {
            response.setContent(ticketService.requestAlipay(orderID));
        }
        return response;
    }

    @RequestMapping("/alipay/return")
    public String alipay_return(HttpServletRequest request) throws Exception {
        boolean signVerify = verifySign(request);
        if (signVerify) {
            //我们自己的订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            if (ticketService.getOrderStatus(Long.parseLong(out_trade_no)).isSuccess()) {
                return "user/paySuccess";
            } else {
                return "user/payWaiting";
            }
        } else {
            return "user/payFailed";
        }
    }

    @ResponseBody
    @PostMapping("/alipay/notify")
    public String alipay_notify(HttpServletRequest request) throws Exception {
        boolean signVerify = verifySign(request);
        if (signVerify) {//验证成功
            //我们的订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");
            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
            if (trade_status.equals("TRADE_FINISHED")) {
                return "success";
            } else if (trade_status.equals("TRADE_SUCCESS")) {
                return ticketService.payOrder(Long.parseLong(out_trade_no), Float.parseFloat(total_amount)).isSuccess() ?
                        "success" : "fail";
            } else {
                return "fail";
            }
        } else {
            //验证失败
            return "fail";
        }
    }

    @RequestMapping("/user/testAli")
    public String testAli() {
        return "user/testAli";
    }

    private boolean verifySign(HttpServletRequest request) throws AlipayApiException {
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (String name : requestParams.keySet()) {
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        return AlipaySignature.rsaCheckV1(
                params,
                alipayProperties.getAliPublicKey(),
                alipayProperties.getCharset(),
                alipayProperties.getSignType()
        );
    }
}
