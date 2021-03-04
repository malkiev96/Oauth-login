package ru.malkiev.oauth;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.malkiev.oauth.entity.Role;
import ru.malkiev.oauth.entity.User;
import ru.malkiev.oauth.repository.RoleRepository;
import ru.malkiev.oauth.service.UserService;

@Component
@AllArgsConstructor
public class UserInit implements ApplicationRunner {

    private final UserService userService;
    private final RoleRepository roleRepository;

    @Override
    public void run(ApplicationArguments args) {
        Role roleUser = roleRepository.save(new Role(null, Role.Code.ROLE_USER, "Пользователь"));
        Role roleAdmin = roleRepository.save(new Role(null, Role.Code.ROLE_ADMIN, "Администратор"));

        userService.create(new User(null, "user", "Юзер", "user",
                "user@user.ru", true, roleUser));
        userService.create(new User(null, "admin", "Админ", "admin",
                "admin@admin.ru", true, roleAdmin));

    }
}
