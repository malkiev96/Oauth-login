package ru.malkiev.oauth;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.malkiev.oauth.entity.Role;
import ru.malkiev.oauth.entity.User;
import ru.malkiev.oauth.repository.RoleRepository;
import ru.malkiev.oauth.repository.UserRepository;
import ru.malkiev.oauth.service.UserService;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
@AllArgsConstructor
@Log4j2
public class UserInit implements ApplicationRunner {

    private final UserService userService;
    private final RoleRepository roleRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Role roleUser = new Role();
        roleUser.setName("ROLE_USER");
        Role roleAdmin = new Role();
        roleAdmin.setName("ROLE_ADMIN");

        roleUser = roleRepository.save(roleUser);
        roleAdmin = roleRepository.save(roleAdmin);

        User admin = new User();
        admin.setEnabled(true);
        admin.setUsername("admin");
        admin.setPassword("admin");

        admin.setRole(roleAdmin);

        User user = new User();
        user.setEnabled(true);
        user.setUsername("user");
        user.setPassword("user");

        user.setRole(roleUser);

        userService.save(user);
        userService.save(admin);

        log.info(userService.getAllUsers());
        log.info(roleRepository.findAll());

    }
}
