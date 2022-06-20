package ru.malkiev.users.service;

import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.malkiev.users.entity.User;
import ru.malkiev.users.exception.UserNotFoundException;
import ru.malkiev.users.repository.UserRepository;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class SubscribersService {

  private final CurrentUser currentUser;
  private final UserRepository userRepository;

  public void subscribeToUser(@NonNull Long userId) {
    User current = currentUser.getUser();
    User subscriber = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));
    if (!current.getFriends().contains(subscriber)) {
      current.getFriends().add(subscriber);
      userRepository.save(current);
    }
  }

  public void unsubscribe(@NonNull Long userId) {
    User current = currentUser.getUser();
    User subscriber = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));
    if (current.getFriends().remove(subscriber)) {
      userRepository.save(current);
    }
  }

}
