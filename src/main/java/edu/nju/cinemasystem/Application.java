package edu.nju.cinemasystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
@MapperScan(basePackages = "edu.nju.cinemasystem.dataservices")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
