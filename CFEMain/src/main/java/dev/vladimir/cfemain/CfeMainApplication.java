package dev.vladimir.cfemain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

@SpringBootApplication
@EnableFeignClients("dev.vladimir")
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class CfeMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(CfeMainApplication.class, args);
    }
}