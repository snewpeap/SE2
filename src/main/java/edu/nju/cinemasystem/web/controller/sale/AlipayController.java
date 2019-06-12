package edu.nju.cinemasystem.web.controller.sale;

import com.alipay.api.AlipayApiException;
import edu.nju.cinemasystem.blservices.sale.ticket.Ticket;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.web.config.CustomWebSecurityConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller("user/alipay")
public class AlipayController {
    @Autowired
    private Ticket ticket;

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

}
