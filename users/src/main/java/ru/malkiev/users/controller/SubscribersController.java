package ru.malkiev.users.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.malkiev.users.entity.User;
import ru.malkiev.users.repository.UserRepository;
import ru.malkiev.users.service.SubscribersService;

@RestController
@AllArgsConstructor
@RequestMapping("/subscribers")
public class SubscribersController {

  private final UserRepository userRepository;
  private final SubscribersService service;

  @GetMapping("/{userId}")
  public ResponseEntity<List<User>> getSubscribers(@PathVariable Long userId) {
    return ResponseEntity.of(
        userRepository.findById(userId).map(User::getFriends)
    );
  }

  @PutMapping("/{userId}")
  public void subscribeToUser(@PathVariable Long userId) {
    service.subscribeToUser(userId);
  }

  @DeleteMapping("/{userId}")
  public void unsubscribe(@PathVariable Long userId) {
    service.unsubscribe(userId);
  }

}
