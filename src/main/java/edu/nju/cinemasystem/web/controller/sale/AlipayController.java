package edu.nju.cinemasystem.web.controller.sale;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import edu.nju.cinemasystem.blservices.sale.ticket.Ticket;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.util.properties.AlipayProperties;
import edu.nju.cinemasystem.web.config.CustomWebSecurityConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Controller("user/alipay")
public class AlipayController {
    @Autowired
    private Ticket ticket;
    @Autowired
    private AlipayProperties alipayProperties;

    @RequestMapping("/pay/{orderID}")
    public Response alipay(@PathVariable long orderID, @RequestBody int couponID, HttpSession session) throws AlipayApiException {
        int userID = (Integer) session.getAttribute(CustomWebSecurityConfiguration.KEY_ID);
        Response response = ticket.payable(orderID, couponID, userID);
        if (response.isSuccess()) {
            return Response.success(ticket.requestAlipay(orderID));
        } else {
            return response;
        }
    }

    @RequestMapping("/return")
    public Response alipay_return(HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean signVerify = verifySign(request);
        Response payResponse = Response.success();
        if (signVerify) {
            //我们自己的订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

            Map<String, String> payData = new HashMap<>(2);
            payData.put("orderID", out_trade_no);
            payData.put("amount", total_amount);
            payResponse.setContent(payData);
        } else {
            payResponse = Response.fail();
        }
        return payResponse;
    }

    @ResponseBody
    @PostMapping("/notify")
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
                ticket.payOrder(Long.parseLong(out_trade_no), Float.parseFloat(total_amount));
            }
            return "success";
        } else {
            //验证失败
            return "fail";
        }
    }

    private boolean verifySign(HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException {
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (String name : requestParams.keySet()) {
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
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
