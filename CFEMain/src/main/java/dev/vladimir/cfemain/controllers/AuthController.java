package dev.vladimir.cfemain.controllers;

import dev.vladimir.cfemain.loggerbot.Logger;
import dev.vladimir.cfemain.user.models.SimpleUser;
import dev.vladimir.cfemain.user.models.UserEntity;
import dev.vladimir.cfemain.user.service.UserService;
import dev.vladimir.cfemain.user.validation.ValidationObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("/reg")
    public String getRegistrationPage() {
        Logger.log("getRegistrationPage()");
        return "reg";
    }

    @PostMapping("/reg")
    public Model addNewUser(Model model, SimpleUser simpleUser) {
        Logger.log("addNewUser(): " + simpleUser);
        ValidationObject<UserEntity> validation = userService.validateNewUser(simpleUser);

        if(validation.hasErrors()) {
            Logger.log("errors: " + Arrays.toString(validation.getErrors().toArray()));

            return model.addAttribute("errors",validation.getErrors());
        }

        Logger.log("success registration: " + simpleUser);
        return model.addAttribute("success", true);
    }
}
