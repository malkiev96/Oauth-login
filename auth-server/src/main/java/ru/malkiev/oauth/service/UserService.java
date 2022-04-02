package ru.malkiev.oauth.service;

import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.malkiev.oauth.dto.UserDto;

public interface UserService extends UserDetailsService, UserDetailsPasswordService {

  void createUser(UserDto dto) throws IllegalArgumentException;

}
