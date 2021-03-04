package ru.malkiev.oauth.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.malkiev.oauth.entity.User;

public interface UserService extends UserDetailsService {

    User create(User user);

}
