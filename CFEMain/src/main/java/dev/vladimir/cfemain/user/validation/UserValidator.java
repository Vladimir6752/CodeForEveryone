package dev.vladimir.cfemain.user.validation;

import dev.vladimir.cfemain.user.repo.UserRepo;
import dev.vladimir.cfemain.user.models.SimpleUser;
import dev.vladimir.cfemain.user.models.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserValidator {
    private UserRepo userRepo;
    public static final int MIN_USER_PASSWORD_LENGTH = 5;

    public ValidationObject<UserEntity> validateNewUser(SimpleUser simpleUser) {
        ValidationObject<UserEntity> userValidation = new ValidationObject<>();

        validateUserAnExisting(simpleUser.getUsername(), userValidation);
        validatePasswordLength(simpleUser.getPassword(), userValidation);

        return userValidation;
    }

    private void validateUserAnExisting(String username, ValidationObject<UserEntity> userValidation) {
        if(userRepo.findByUsername(username) != null) {
            userValidation.addError("Пользователь с таким именем уже существует");
        }
    }

    private void validatePasswordLength(String password, ValidationObject<UserEntity> userValidation) {
        if(password.length() < MIN_USER_PASSWORD_LENGTH) {
            userValidation.addError(
                    String.format("Пароль должен быть не менее %s символов", MIN_USER_PASSWORD_LENGTH)
            );
        }
    }
}
