package dev.vladimir.cfemain.config;

import dev.vladimir.cfemain.user.models.UserEntity;
import dev.vladimir.cfemain.user.models.UserRole;
import dev.vladimir.cfemain.user.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final String ADMIN_NAME = "ADMIN";
    private final String ADMIN_PASSWORD = "PASS";
    private final UserRepo userRepo;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        UserEntity foundedAdmin = userRepo.findByUsername(ADMIN_NAME);

        if (foundedAdmin != null) return;

        UserEntity admin = new UserEntity();
        admin.setPassword(ADMIN_PASSWORD);
        admin.setUsername(ADMIN_NAME);
        admin.setRoles(Set.of(UserRole.USER, UserRole.ADMIN));
        userRepo.save(admin);
    }
}
