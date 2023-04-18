package dev.vladimir.cfemain.controllers;

import dev.vladimir.cfemain.user.models.SimpleUser;
import dev.vladimir.cfemain.user.models.UserEntity;
import dev.vladimir.cfemain.user.service.UserService;
import dev.vladimir.cfemain.user.validation.ValidationObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    @Test
    void addNewUser_shouldReturnSuccessResult() {
        Model modelMock = mock(Model.class);
        SimpleUser simpleUser = new SimpleUser("username", "password");
        when(userService.validateNewUser(simpleUser)).thenReturn(new ValidationObject<>());

        authController.addNewUser(modelMock, simpleUser);

        verify(userService).validateNewUser(simpleUser);
        verify(modelMock).addAttribute("success", true);
        verify(modelMock).addAttribute(any(), any());
    }

    @Test
    void addNewUser_shouldReturnErrorResult() {
        Model modelMock = mock(Model.class);
        SimpleUser simpleUser = new SimpleUser("username", "password");
        ValidationObject<UserEntity> validationObject = mock(ValidationObject.class);
        when(validationObject.hasErrors()).thenReturn(true);
        when(userService.validateNewUser(simpleUser)).thenReturn(validationObject);

        authController.addNewUser(modelMock, simpleUser);

        verify(userService).validateNewUser(simpleUser);
        verify(modelMock).addAttribute("errors", validationObject.getErrors());
        verify(modelMock).addAttribute(any(), any());
    }
}