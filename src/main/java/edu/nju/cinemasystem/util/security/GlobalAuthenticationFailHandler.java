package edu.nju.cinemasystem.util.security;

import edu.nju.cinemasystem.util.properties.message.AccountMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class GlobalAuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {
    @Autowired
    private AccountMsg accountMsg;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        System.out.println(exception.getMessage());
        response.setStatus(401);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain; charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write(exception.getMessage());
    }
}
