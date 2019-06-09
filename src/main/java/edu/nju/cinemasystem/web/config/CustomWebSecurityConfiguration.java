package edu.nju.cinemasystem.web.config;

import edu.nju.cinemasystem.util.security.GlobalUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class CustomWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    GlobalUserDetailService globalUserDetailService(){
        return new GlobalUserDetailService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(globalUserDetailService());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //TODO
        super.configure(http);
    }
}
