package ru.malkiev.oauth.application.service;

import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.malkiev.oauth.api.dto.UserDto;

public interface UserService extends UserDetailsService, UserDetailsPasswordService {

  void createUser(UserDto dto) throws IllegalArgumentException;

}
