package edu.nju.cinemasystem.web.controller.user;

import edu.nju.cinemasystem.blservices.user.Account;
import edu.nju.cinemasystem.web.config.InterceptorConfiguration;
import edu.nju.cinemasystem.data.vo.Form.RegistryForm;
import edu.nju.cinemasystem.data.vo.Form.UserForm;
import edu.nju.cinemasystem.data.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by weijin on 2019/6/4
 */
@RestController
public class AccountController {
    @Autowired
    private Account account;

    @PostMapping("/login")
    public Response login(@RequestBody UserForm userForm, HttpSession session){
        Response userResponse = account.login(userForm);
        if(userResponse.getContent() != null) {
            session.setAttribute(InterceptorConfiguration.SESSION_KEY, userForm);
        }
        return userResponse;
    }

    @PostMapping("/register")
    public Response register(@RequestBody RegistryForm registryForm){
        return account.register(registryForm);
    }

    @PostMapping("/logout")
    public String logOut(HttpSession session){
        session.removeAttribute(InterceptorConfiguration.SESSION_KEY);
        return "index";
    }
}
