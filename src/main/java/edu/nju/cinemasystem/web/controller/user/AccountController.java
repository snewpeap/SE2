package edu.nju.cinemasystem.web.controller.user;

import edu.nju.cinemasystem.blservices.user.Account;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.UserVO;
import edu.nju.cinemasystem.data.vo.form.RegistryForm;
import edu.nju.cinemasystem.data.vo.form.UserForm;
import edu.nju.cinemasystem.util.properties.RoleProperty;
import edu.nju.cinemasystem.util.security.GlobalUserDetailService;
import edu.nju.cinemasystem.web.config.InterceptorConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    @Autowired
    private GlobalUserDetailService userDetailService;
    @Autowired
    private RoleProperty roleProperty;

    @PostMapping("/login")
    public Response login(@RequestBody UserForm userForm, HttpSession session, HttpServletResponse servletResponse) throws IOException {
        Response response = account.login(userForm);
        if (response.isSuccess()) {
            UserVO userVO = (UserVO) response.getContent();
            session.setAttribute(InterceptorConfiguration.SESSION_KEY, userVO);
            servletResponse.sendRedirect(
                    userDetailService.loadUserByUsername(userVO.getName())
                            .getAuthorities()
                            .contains(new SimpleGrantedAuthority(roleProperty.getAudience())) ?
                            "/home" : "/admin/movieManage"
            );
        }
        return response;
    }

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
        servletResponse.sendRedirect("/home");
    }
}
