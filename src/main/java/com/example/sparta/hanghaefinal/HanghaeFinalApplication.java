package com.example.sparta.hanghaefinal;

import com.example.sparta.hanghaefinal.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableConfigurationProperties(AppProperties.class)
@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication
public class HanghaeFinalApplication {

    public static void main(String[] args) {
        SpringApplication.run(HanghaeFinalApplication.class, args);
    }

}
