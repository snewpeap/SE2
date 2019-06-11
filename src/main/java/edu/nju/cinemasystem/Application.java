package edu.nju.cinemasystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableConfigurationProperties
@EnableGlobalMethodSecurity(securedEnabled = true)
@MapperScan(basePackages = "edu.nju.cinemasystem.dataservices")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
