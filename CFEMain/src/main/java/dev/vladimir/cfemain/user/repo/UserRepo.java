package dev.vladimir.cfemain.user.repo;

import dev.vladimir.cfemain.user.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
}
