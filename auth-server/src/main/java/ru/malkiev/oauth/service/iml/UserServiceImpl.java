package ru.malkiev.oauth.service.iml;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.malkiev.oauth.dto.UserDto;
import ru.malkiev.oauth.entity.CustomUserDetails;
import ru.malkiev.oauth.entity.Role;
import ru.malkiev.oauth.entity.User;
import ru.malkiev.oauth.repository.UserRepository;
import ru.malkiev.oauth.service.RoleService;
import ru.malkiev.oauth.service.UserService;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final RoleService roleService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user;
    if (EmailValidator.getInstance().isValid(username)) {
      user = userRepository.findByEmail(username)
          .orElseThrow(() -> new UsernameNotFoundException(username));
    } else {
      user = userRepository.findByUsername(username)
          .orElseThrow(() -> new UsernameNotFoundException(username));
    }
    return CustomUserDetails.of(user);
  }

  @Override
  public void createUser(UserDto dto) throws IllegalArgumentException {
    User user = new User();

    String username = dto.getUsername();
    boolean usernameNotPresent = !userRepository.findByUsername(username).isPresent();
    Assert.isTrue(usernameNotPresent, format("Пользователь %s уже зарегистрирован", username));

    String email = dto.getEmail();
    boolean emailNotPresent = !userRepository.findByEmail(email).isPresent();
    Assert.isTrue(emailNotPresent, format("Email %s уже зарегистрирован в системе", email));

    user.setUsername(username);
    user.setFirstName(dto.getFirstname());
    user.setLastname(dto.getLastname());
    user.setEmail(email);
    user.setEnabled(true);
    user.setLocked(false);
    user.setPassword(passwordEncoder.encode(dto.getPassword()));
    user.getRoles().addAll(roleService.getDefaultRoles());

    User savedUser = userRepository.save(user);
    log.info("User created: {}", savedUser);
  }

  @Override
  public UserDetails updatePassword(UserDetails userDetails, String s) {
    throw new UnsupportedOperationException("Not implemented");
  }

  private Collection<? extends GrantedAuthority> getAuthorities(User user) {
    List<GrantedAuthority> authorities = new ArrayList<>();
    for (Role role : user.getRoles()) {
      authorities.add(new SimpleGrantedAuthority(role.getCode()));
    }
    return authorities;
  }

}
