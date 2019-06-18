package edu.nju.cinemasystem.util.security;

import edu.nju.cinemasystem.blservices.user.AccountService;
import edu.nju.cinemasystem.data.vo.UserVO;
import edu.nju.cinemasystem.util.properties.RoleProperty;
import edu.nju.cinemasystem.web.config.CustomWebSecurityConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

@Component
public class GlobalAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired
    private AccountService accountService;
    @Autowired
    private RoleProperty roleProperty;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = authentication.getName();
        UserVO userVO = accountService.getUserVOByName(username);
        session.setAttribute(CustomWebSecurityConfiguration.KEY_ID, userVO.getID());
        session.setAttribute(CustomWebSecurityConfiguration.KEY_NAME, username);
        System.out.println(username + "登录成功");
        response.addCookie(generateCookie("id", String.valueOf(userVO.getID())));
        response.addCookie(generateCookie("username", username));
        response.addCookie(generateCookie("role", getRole(authentication.getAuthorities())));
    }

    private Cookie generateCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60);
        return cookie;
    }

    private String getRole(Collection<? extends GrantedAuthority> auths) {
        int highest = roleProperty.getRoleLevel(roleProperty.getAudience());
        for (GrantedAuthority auth : auths) {
            int level = roleProperty.getRoleLevel(auth.getAuthority().replace("ROLE_", ""));
            if (level > highest) {
                highest = level;
            }
        }
        return roleProperty.getRole(highest);
    }
}
