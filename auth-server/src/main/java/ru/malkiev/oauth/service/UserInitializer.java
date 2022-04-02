package ru.malkiev.oauth.service;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.malkiev.oauth.dto.UserDto;
import ru.malkiev.oauth.entity.Role;
import ru.malkiev.oauth.repository.RoleRepository;

@Component
@AllArgsConstructor
public class UserInitializer implements ApplicationRunner {

  private final UserService userService;
  private final RoleRepository roleRepository;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    roleRepository.save(new Role(1L, "ROLE_USER", "Пользователь", true));
    roleRepository.save(new Role(2L, "ROLE_ADMIN", "Администратор", false));

    UserDto dto = new UserDto();
    dto.setEmail("admin@admin.ru");
    dto.setUsername("Admin");
    dto.setFirstname("admin");
    dto.setLastname("admin");
    dto.setPassword("password");

    userService.createUser(dto);
  }
}
