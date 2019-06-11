package edu.nju.cinemasystem.web.controller.user;

import edu.nju.cinemasystem.blservices.user.Account;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.RegistryForm;
import edu.nju.cinemasystem.util.properties.RoleProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

/**
 * Created by weijin on 2019/6/4
 */
@RestController
public class AccountController {
    private final Account account;
    private final RoleProperty roleProperty;

    @Autowired
    public AccountController(Account account, RoleProperty roleProperty) {
        this.account = account;
        this.roleProperty = roleProperty;
    }

    @RequestMapping("/index")
    public void getIndex(HttpServletResponse response) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String redirect = auth.getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_" + roleProperty.getAudience())) ?
                "/user/home" : "/manage/movie";
        response.sendRedirect(redirect);
    }


    @PostMapping("/register")
    public Response register(@RequestBody @Valid RegistryForm registryForm, HttpServletResponse servletResponse) throws IOException {
        Response response = account.register(registryForm);
        if (response.isSuccess()) {
            servletResponse.sendRedirect("/login");
        }
        return response;
    }
}
