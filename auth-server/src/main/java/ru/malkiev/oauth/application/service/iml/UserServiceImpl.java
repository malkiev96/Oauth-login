package ru.malkiev.oauth.application.service.iml;

import static java.lang.String.format;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.malkiev.oauth.api.dto.UserDto;
import ru.malkiev.oauth.application.repository.UserRepository;
import ru.malkiev.oauth.application.service.RoleService;
import ru.malkiev.oauth.application.service.UserService;
import ru.malkiev.oauth.domain.AppUserDetails;
import ru.malkiev.oauth.domain.User;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final RoleService roleService;
  private static final EmailValidator EMAIL = EmailValidator.getInstance();

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user =
        EMAIL.isValid(username)
            ? userRepository.findByEmail(username)
            : userRepository.findByUsername(username);
    return user.map(AppUserDetails::new).orElseThrow(() -> new UsernameNotFoundException(username));
  }

  @Override
  @Transactional
  public void createUser(UserDto dto) throws IllegalArgumentException {
    User user = new User();

    String username = dto.getUsername();
    boolean usernameNotPresent = userRepository.findByUsername(username).isEmpty();
    Assert.isTrue(usernameNotPresent, format("Пользователь %s уже зарегистрирован", username));

    String email = dto.getEmail();
    boolean emailNotPresent = userRepository.findByEmail(email).isEmpty();
    Assert.isTrue(emailNotPresent, format("Email %s уже зарегистрирован в системе", email));

    user.setUsername(username);
    user.setFirstName(dto.getFirstname());
    user.setLastname(dto.getLastname());
    user.setEmail(email);
    user.setEnabled(true);
    user.setLocked(false);
    user.setPassword(passwordEncoder.encode(dto.getPassword()));
    user.getRoles().addAll(roleService.getDefaultRoles());

    userRepository.save(user);
    log.info("User created: {}", user);
  }

  @Override
  public UserDetails updatePassword(UserDetails userDetails, String s) {
    throw new UnsupportedOperationException("Not implemented");
  }

}
