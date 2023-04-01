package dev.vladimir.cfemain.user.service;

import dev.vladimir.cfemain.user.models.SimpleUser;
import dev.vladimir.cfemain.user.models.UserEntity;
import dev.vladimir.cfemain.user.repo.UserRepo;
import dev.vladimir.cfemain.user.validation.UserValidator;
import dev.vladimir.cfemain.user.validation.ValidationObject;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final UserValidator userValidator;

    private UserEntity saveNewUser(SimpleUser simpleUser) {
        return userRepo.save(new UserEntity(simpleUser));
    }

    public ValidationObject<UserEntity> validateNewUser(SimpleUser simpleUser) {
        ValidationObject<UserEntity> validation = userValidator.validateNewUser(simpleUser);

        if(validation.hasNoErrors()) {
            validation.setValue(saveNewUser(simpleUser));
        }

        return validation;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity user = userRepo.findByUsername(s);

        if(user == null)
            throw new UsernameNotFoundException(
                    String.format("User with name %s not found!", s)
            );

        return user;
    }
}
