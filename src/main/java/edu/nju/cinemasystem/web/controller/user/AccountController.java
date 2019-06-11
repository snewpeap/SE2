package edu.nju.cinemasystem.web.controller.user;

import edu.nju.cinemasystem.blservices.user.Account;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.RegistryForm;
import edu.nju.cinemasystem.web.config.InterceptorConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by weijin on 2019/6/4
 */
@RestController
public class AccountController {
    @Autowired
    private Account account;

    @PostMapping("/register")
    public Response register(@RequestBody RegistryForm registryForm, HttpSession session, HttpServletResponse servletResponse) throws IOException {
        Response response = account.register(registryForm);
        if (response.isSuccess()) {
            session.setAttribute(InterceptorConfiguration.SESSION_KEY, response.getContent());
            servletResponse.sendRedirect("/home");
        }
        return response;
    }

    @PostMapping("/logout")
    public void logOut(HttpSession session, HttpServletResponse servletResponse) throws IOException {
        session.removeAttribute(InterceptorConfiguration.SESSION_KEY);
        servletResponse.sendRedirect("/index");
    }
}
