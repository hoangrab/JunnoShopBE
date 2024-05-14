package com.juno.config;

import com.juno.entity.Role;
import com.juno.entity.User;
import com.juno.repository.RoleRepo;
import com.juno.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;


@Configuration
@RequiredArgsConstructor
public class DefaultData implements ApplicationRunner {
    private final PasswordEncoder encoder;
    private final RoleRepo roleRepo;
    private final UserRepo userRepo;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createRole();
        createAdmin();
    }

    public void createRole() {
        if(roleRepo.count() == 0) {
            Role role1 = new Role();
            role1.setName("ROLE_ADMIN");
            Role role2 = new Role();
            role2.setName("ROLE_USER");
            roleRepo.saveAll(List.of(role1,role2));
        }
    }

    public void createAdmin() {
        if(userRepo.count() == 0) {
            User user = new User();
            Role role = roleRepo.findByName("ROLE_ADMIN");
            user.setRole(role);
            user.setGmail("admin");
            user.setEnabled(true);
            user.setPassword(encoder.encode("123456"));
            user.setFullName("ADMIN VIP");
            userRepo.save(user);
        }
    }
}
