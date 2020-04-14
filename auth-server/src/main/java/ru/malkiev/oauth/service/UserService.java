package ru.malkiev.oauth.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.malkiev.oauth.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    List<User> getAllUsers();

    List<User> getUsersByRoleName(String role);

    Optional<User> getUserById(long id);

    User save(User user);

    void delete(long id);

    List<User> searchUserByUsername(String username);
}
