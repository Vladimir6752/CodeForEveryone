package dev.vladimir.cfemain.user.validation;

import dev.vladimir.cfemain.user.models.SimpleUser;
import dev.vladimir.cfemain.user.models.UserEntity;
import dev.vladimir.cfemain.user.repo.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static dev.vladimir.cfemain.user.validation.UserValidator.MIN_USER_PASSWORD_LENGTH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserValidatorTest {
    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserValidator userValidator;

    @Test
    void validateNewUser_shouldSayErrorUserIsExists() {
        String username = "username";
        SimpleUser simpleUser = new SimpleUser(username, "password");
        when(userRepo.findByUsername(username)).thenReturn(mock(UserEntity.class));

        ValidationObject<UserEntity> validation = userValidator.validateNewUser(simpleUser);

        verify(userRepo).findByUsername(username);
        assertEquals(validation.getErrors(), List.of("Пользователь с таким именем уже существует"));
    }

    @Test
    void validateNewUser_shouldSayErrorSmallPassword() {
        String username = "username";
        when(userRepo.findByUsername(username)).thenReturn(null);

        ValidationObject<UserEntity> validation = userValidator.validateNewUser(new SimpleUser(username, "pass"));

        verify(userRepo).findByUsername(username);
        assertEquals(validation.getErrors(), List.of(String.format("Пароль должен быть не менее %s символов", MIN_USER_PASSWORD_LENGTH)));
    }

    @Test
    void validateNewUser_shouldSayAllErrors() {
        String username = "username";
        SimpleUser simpleUser = new SimpleUser(username, "pass");
        when(userRepo.findByUsername(username)).thenReturn(mock(UserEntity.class));

        ValidationObject<UserEntity> validation = userValidator.validateNewUser(simpleUser);

        verify(userRepo).findByUsername(username);
        assertEquals(
                validation.getErrors(),
                List.of(
                        "Пользователь с таким именем уже существует",
                        String.format("Пароль должен быть не менее %s символов", MIN_USER_PASSWORD_LENGTH)
                )
        );
    }


}