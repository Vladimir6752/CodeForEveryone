package dev.vladimir.cfemain.controllers;

import dev.vladimir.cfemain.user.models.SimpleUser;
import dev.vladimir.cfemain.user.models.UserEntity;
import dev.vladimir.cfemain.user.service.UserService;
import dev.vladimir.cfemain.user.validation.ValidationObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("/reg")
    public String registration() {
        return "reg";
    }

    @PostMapping("/reg")
    public Model addNewUser(Model model, SimpleUser simpleUser) {
        ValidationObject<UserEntity> validation = userService.validateNewUser(simpleUser);

        if(validation.hasNoErrors())
            return model.addAttribute("success");

        return model.addAttribute("errors",validation.getErrors());
    }
}
