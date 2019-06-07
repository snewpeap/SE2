package edu.nju.cinemasystem.web.config;

import edu.nju.cinemasystem.web.interceptor.SessionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {
    public final static String SESSION_KEY = "user";

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionInterceptor())
                .excludePathPatterns(
                        "/login",
                        "/index",
                        "/signUp",
                        "/register",
                        "/error",
                        "/**/*.css",
                        "/**/*.js",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.jpg",
                        "/**/*.jpeg",
                        "/font/**"
                )
                .addPathPatterns("/**");
    }
}
