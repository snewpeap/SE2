package edu.nju.cinemasystem.web.config;

import edu.nju.cinemasystem.util.properties.RoleProperty;
import edu.nju.cinemasystem.util.security.GlobalAuthenticationFailHandler;
import edu.nju.cinemasystem.util.security.GlobalAuthenticationProvider;
import edu.nju.cinemasystem.util.security.GlobalAuthenticationSuccessHandler;
import edu.nju.cinemasystem.util.security.GlobalUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class CustomWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final RoleProperty roleProperty;
    private GlobalUserDetailService globalUserDetailService;
    private GlobalAuthenticationSuccessHandler successHandler;
    private GlobalAuthenticationFailHandler failHandler;

    @Autowired
    public CustomWebSecurityConfiguration(RoleProperty roleProperty, GlobalUserDetailService globalUserDetailService, GlobalAuthenticationSuccessHandler successHandler, GlobalAuthenticationFailHandler failHandler) {
        this.roleProperty = roleProperty;
        this.globalUserDetailService = globalUserDetailService;
        this.successHandler = successHandler;
        this.failHandler = failHandler;
    }

    @Bean(name = "passwordEncoder")
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(name = "globalAuthenticationProvider")
    public AuthenticationProvider globalAuthenticationProvider() {
        GlobalAuthenticationProvider authenticationProvider = new GlobalAuthenticationProvider();
        authenticationProvider.setUserDetailsService(globalUserDetailService);
        return authenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(globalAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/index", "/register", "/logout", "/login", "/error").permitAll()
                .antMatchers("/manage/**")
                .hasAnyRole(
                        roleProperty.getRoot(), roleProperty.getManager(), roleProperty.getRoot())
                .antMatchers("/admin/**")
                .hasAnyRole(
                        roleProperty.getRoot(), roleProperty.getManager())
                .antMatchers("/root/**")
                .hasRole(roleProperty.getRoot())
                .regexMatchers("^/(?!manage|admin|root|index|register|login|logout|error).*$")
                .hasRole(roleProperty.getAudience())
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successHandler(successHandler)
                .failureHandler(failHandler)
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .deleteCookies()
                .logoutSuccessUrl("/login")
                .permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
                .expiredUrl("/login")
                .and()
                .and()
                .csrf()
                .disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/favicon.ico", "/images/**", "/font/**", "/webjars/**", "**/*.css", "**/*.js");
    }
}
