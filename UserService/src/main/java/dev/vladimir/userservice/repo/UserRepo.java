package dev.vladimir.userservice.repo;

import dev.vladimir.userservice.models.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<UserEntity, Long> {
    UserEntity findByUserName(String username);
}
