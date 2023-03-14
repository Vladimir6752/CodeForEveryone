package dev.vladimir.codeservice.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@FeignClient(name = "users_service", url = "localhost:90/users")
public interface UserServiceFeignClient {
    @GetMapping("validate")
    ResponseEntity<Boolean> validateUser(
            @RequestParam String username,
            @RequestHeader(AUTHORIZATION) String authUuid
    );
}
