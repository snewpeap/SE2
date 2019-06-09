package edu.nju.cinemasystem.web.config;

import edu.nju.cinemasystem.util.properties.RoleProperty;
import edu.nju.cinemasystem.util.security.GlobalUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

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

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(globalUserDetailService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/index","/register","/logout").permitAll()
                .antMatchers("/manage/**").hasAnyRole(
                        roleProperty.getRoot(), roleProperty.getManager(),roleProperty.getRoot())
                .antMatchers("/admin/**").hasAnyRole(
                        roleProperty.getRoot(),roleProperty.getManager())
                .antMatchers("/root/**").hasRole(roleProperty.getRoot())
                .regexMatchers("^/(?!manage|admin|root).*$").hasRole(roleProperty.getAudience())
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/index")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .permitAll();
    }
}
