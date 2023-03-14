package dev.vladimir.userservice.service;

import dev.vladimir.userservice.models.SimpleUser;
import dev.vladimir.userservice.models.UserEntity;
import dev.vladimir.userservice.repo.UserRepo;
import dev.vladimir.userservice.validation.UserValidator;
import dev.vladimir.userservice.validation.ValidationObject;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepo userRepo;
    private UserValidator userValidator;

    public UserEntity saveNewUser(SimpleUser simpleUser) {
        return userRepo.save(new UserEntity(simpleUser));
    }

    public ValidationObject<UserEntity> validateUserByPassword(SimpleUser simpleUser) {
        ValidationObject<UserEntity> validation = userValidator.validateUserByPassword(simpleUser);

        if(validation.noErrors()) {
            validation.setValue(userRepo.findByUserName(simpleUser.getUsername()));
        }

        return validation;
    }

    public ValidationObject<UserEntity> validateNewUser(SimpleUser simpleUser) {
        ValidationObject<UserEntity> validation = userValidator.validateNewUser(simpleUser);

        if(validation.noErrors()) {
            validation.setValue(saveNewUser(simpleUser));
        }

        return validation;
    }

    public boolean validateUserByUUID(String username, String uuid) {
        return userValidator.validateUserByUUID(username, uuid);
    }
}
