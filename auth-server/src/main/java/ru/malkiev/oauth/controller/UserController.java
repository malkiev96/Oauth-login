package ru.malkiev.oauth.controller;

import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.malkiev.oauth.assembler.UserModelAssembler;
import ru.malkiev.oauth.entity.User;
import ru.malkiev.oauth.exception.UserNotFoundException;
import ru.malkiev.oauth.model.AuthoritiesModel;
import ru.malkiev.oauth.model.UserModel;
import ru.malkiev.oauth.repository.UserRepository;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserModelAssembler assembler;
    private final UserRepository userRepository;

    @GetMapping("/user/me")
    public ResponseEntity<AuthoritiesModel> user() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(new AuthoritiesModel(user));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/users")
    public CollectionModel<UserModel> getAllUsers() {
        return assembler.toCollectionModel(
                userRepository.findAll()
        );
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserModel> getUser(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

}