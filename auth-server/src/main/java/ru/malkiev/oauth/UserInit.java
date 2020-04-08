package ru.malkiev.oauth;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.malkiev.oauth.entity.Role;
import ru.malkiev.oauth.entity.User;
import ru.malkiev.oauth.repository.RoleRepository;
import ru.malkiev.oauth.repository.UserRepository;

@Component
@AllArgsConstructor
public class UserInit implements ApplicationRunner {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder encoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Role roleUser = new Role();
        Role roleAdmin = new Role();
        roleUser.setName("USER");
        roleAdmin.setName("ADMIN");
        roleRepository.save(roleUser);
        roleRepository.save(roleAdmin);

        User user = new User();
        user.setEnabled(true);
        user.setPassword(encoder.encode("user"));
        user.setUsername("user");
        userRepository.save(user);
    }
}
