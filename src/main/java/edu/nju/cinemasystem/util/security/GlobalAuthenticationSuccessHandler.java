package edu.nju.cinemasystem.util.security;

import edu.nju.cinemasystem.blservices.user.Account;
import edu.nju.cinemasystem.util.properties.RoleProperty;
import edu.nju.cinemasystem.web.config.InterceptorConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class GlobalAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired
    private Account account;
    @Autowired
    private RoleProperty roleProperty;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = authentication.getName();
        session.setAttribute(InterceptorConfiguration.SESSION_KEY, account.getUserVOByName(username));
        System.out.println("登录成功");
//        request.getRequestDispatcher(authentication.getAuthorities()
//                .contains(new SimpleGrantedAuthority("ROLE_" + roleProperty.getAudience())) ?
//                "/home" : "/admin/movieManage").forward(request,response);
        super.setDefaultTargetUrl(
                authentication.getAuthorities()
                        .contains(new SimpleGrantedAuthority("ROLE_" + roleProperty.getAudience())) ?
                        "/user/home" : "/manage/movie"
        );
        super.onAuthenticationSuccess(request,response,authentication);
    }
}
