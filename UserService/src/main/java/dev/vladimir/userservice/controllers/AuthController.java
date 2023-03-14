package dev.vladimir.userservice.controllers;

import dev.vladimir.userservice.models.SimpleUser;
import dev.vladimir.userservice.models.UserEntity;
import dev.vladimir.userservice.service.UserService;
import dev.vladimir.userservice.validation.ValidationObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("users")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("validate")
    public ResponseEntity<Boolean> validateUser(@RequestParam String username, @RequestHeader(AUTHORIZATION) String authUuid) {
        boolean isValid = userService.validateUserByUUID(username, authUuid.replace("Bearer ", ""));

        return new ResponseEntity<>(
                isValid ?
                        HttpStatus.OK
                        :
                        HttpStatus.FORBIDDEN
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ValidationObject<UserEntity>> login(@RequestBody SimpleUser simpleUser) {
        ValidationObject<UserEntity> validation = userService.validateUserByPassword(simpleUser);

        return new ResponseEntity<>(validation, validation.getStatus());
    }

    @PostMapping("/reg")
    public ResponseEntity<ValidationObject<UserEntity>> registration(@RequestBody SimpleUser simpleUser) {
        ValidationObject<UserEntity> validation = userService.validateNewUser(simpleUser);

        return new ResponseEntity<>(validation, validation.getStatus());
    }
}
