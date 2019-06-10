package edu.nju.cinemasystem.web.config;

import edu.nju.cinemasystem.util.properties.RoleProperty;
import edu.nju.cinemasystem.util.security.GlobalUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    @Autowired
    public CustomWebSecurityConfiguration(RoleProperty roleProperty, GlobalUserDetailService globalUserDetailService) {
        this.roleProperty = roleProperty;
        this.globalUserDetailService = globalUserDetailService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(globalUserDetailService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/index", "/register", "/logout", "/login").permitAll()
                .antMatchers("/manage/**")
                .hasAnyRole(
                        roleProperty.getRoot(), roleProperty.getManager(), roleProperty.getRoot())
                .antMatchers("/admin/**")
                .hasAnyRole(
                        roleProperty.getRoot(), roleProperty.getManager())
                .antMatchers("/root/**")
                .hasRole(roleProperty.getRoot())
                .regexMatchers("^/(?!manage|admin|root|index|register|login|logout).*$")
                .hasRole(roleProperty.getAudience())
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/index")
                .loginProcessingUrl("/login")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .invalidSessionUrl("/index")
                .and()
                .csrf()
                .disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/favicon.ico");
    }
}
