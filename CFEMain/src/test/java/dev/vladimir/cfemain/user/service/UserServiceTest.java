package dev.vladimir.cfemain.user.service;

import dev.vladimir.cfemain.user.models.SimpleUser;
import dev.vladimir.cfemain.user.models.UserEntity;
import dev.vladimir.cfemain.user.repo.UserRepo;
import dev.vladimir.cfemain.user.validation.UserValidator;
import dev.vladimir.cfemain.user.validation.ValidationObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepo userRepo;

    @Mock
    private UserValidator userValidator;

    @InjectMocks
    private UserService userService;

    @Test
    void addSolvedExerciseIdInUser() {
        int exId = 1;
        UserEntity userMock = mock(UserEntity.class);

        userService.addSolvedExerciseIdInUser(exId, userMock);

        verify(userMock).addSolvedExerciseId(exId);
        verify(userRepo).save(userMock);
    }

    @Test
    void validateNewUser_shouldSaveNewUser() {
        SimpleUser simpleUser = new SimpleUser("username", "password");
        ValidationObject<UserEntity> validationMock = mock(ValidationObject.class);
        UserEntity resultUserEntity = new UserEntity(simpleUser);
        when(userValidator.validateNewUser(simpleUser)).thenReturn(validationMock);
        when(validationMock.hasNoErrors()).thenReturn(true);
        when(userRepo.save(any(UserEntity.class))).thenReturn(resultUserEntity);

        userService.validateNewUser(simpleUser);

        verify(userValidator).validateNewUser(simpleUser);
        verify(userRepo).save(any(UserEntity.class));
        verify(validationMock).setValue(resultUserEntity);
    }

    @Test
    void validateNewUser_shouldNotSaveNewUser() {
        SimpleUser simpleUser = new SimpleUser("username", "password");
        ValidationObject<UserEntity> validationMock = mock(ValidationObject.class);
        UserEntity resultUserEntity = new UserEntity(simpleUser);
        when(userValidator.validateNewUser(simpleUser)).thenReturn(validationMock);
        when(validationMock.hasNoErrors()).thenReturn(false);

        userService.validateNewUser(simpleUser);

        verify(userValidator).validateNewUser(simpleUser);
        verifyNoInteractions(userRepo);
        verify(validationMock, times(0)).setValue(resultUserEntity);
    }

    @Test
    void loadUserByUsername_shouldFindUser() {
        String username = "username";
        UserEntity userMock = mock(UserEntity.class);
        when(userRepo.findByUsername(username)).thenReturn(userMock);

        UserDetails foundedUser = userService.loadUserByUsername(username);

        verify(userRepo).findByUsername(username);
        assertInstanceOf(userMock.getClass(), foundedUser);
    }

    @Test
    void loadUserByUsername_shouldNotFindUserAndThrowException() {
        String username = "username";
        when(userRepo.findByUsername(username)).thenReturn(null);

        UsernameNotFoundException usernameNotFoundException = assertThrows(
                UsernameNotFoundException.class,
                () -> userService.loadUserByUsername(username)
        );

        assertEquals(
                String.format("User with name %s not found!", username),
                usernameNotFoundException.getMessage()
        );
    }

    @Test
    void deleteSolvedExerciseIdFromUsers() {
        UserEntity user1 = new UserEntity();
        UserEntity user2 = new UserEntity();
        UserEntity user3 = new UserEntity();
        user1.setSolvedExercisesId(new ArrayList<>(List.of(1, 2, 3)));
        user2.setSolvedExercisesId(new ArrayList<>(List.of(1, 3)));
        user3.setSolvedExercisesId(new ArrayList<>(List.of(2, 3)));
        when(userRepo.existsById(1L)).thenReturn(true);
        when(userRepo.existsById(2L)).thenReturn(true);
        when(userRepo.existsById(3L)).thenReturn(true);
        when(userRepo.getById(1L)).thenReturn(user1);
        when(userRepo.getById(2L)).thenReturn(user2);
        when(userRepo.getById(3L)).thenReturn(user3);

        userService.deleteSolvedExerciseIdFromUsers(2);

        assertEquals(user1.getSolvedExercisesId(), List.of(1, 3));
        assertEquals(user3.getSolvedExercisesId(), List.of(3));
        verify(userRepo).save(user1);
        verify(userRepo, times(0)).save(user2);
        verify(userRepo).save(user3);
    }
}