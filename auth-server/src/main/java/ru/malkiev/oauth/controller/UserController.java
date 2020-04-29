package ru.malkiev.oauth.controller;

import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.malkiev.oauth.exception.RoleNotFoundException;
import ru.malkiev.oauth.exception.UserNotFoundException;
import ru.malkiev.oauth.assembler.UserModelAssembler;
import ru.malkiev.oauth.entity.Role;
import ru.malkiev.oauth.entity.User;
import ru.malkiev.oauth.repository.RoleRepository;
import ru.malkiev.oauth.service.RoleService;
import ru.malkiev.oauth.service.UserService;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final UserModelAssembler userModelAssembler;

    @GetMapping("/user/me")
    public Map<String, Object> user(@AuthenticationPrincipal Principal principal) {
        if (principal != null) {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("name", principal.getName());
            attributes.put("authorities", SecurityContextHolder.getContext().getAuthentication().getAuthorities());
            return attributes;
        }
        return null;
    }

    @GetMapping("/users")
    public CollectionModel<EntityModel<User>> getAllUsers() {
        return userModelAssembler.toCollectionModel(userService.getAllUsers());
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> getUser(@PathVariable long id) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return userModelAssembler.toModel(user);
    }

    @GetMapping("/users/role/{id}")
    public CollectionModel<EntityModel<User>> getAllByRoleId(@PathVariable long id) {
        Role role = roleService.getRoleById(id)
                .orElseThrow(() -> new RoleNotFoundException(id));
        return userModelAssembler.toCollectionModel(role.getUsers());
    }

    @GetMapping("/users/search")
    public CollectionModel<EntityModel<User>> searchByUsername(@RequestParam(value = "username") String username,
                                                               @RequestParam(value = "roleName") String roleName) {
        List<User> users = userService.searchUserByUsername(username);

        if (roleName.isEmpty()) {
            return userModelAssembler.toCollectionModel(users);
        }

        Role role = roleService.getRoleByName(roleName)
                .orElseThrow(() -> new RoleNotFoundException(roleName));

        List<User> usersWithRole = users.stream()
                .filter(user -> user.getRole().equals(role))
                .collect(Collectors.toList());

        return userModelAssembler.toCollectionModel(usersWithRole);

    }

    @PostMapping("/users")
    public ResponseEntity<?> addUser(@RequestBody User user, UriComponentsBuilder uriComponentsBuilder) {
        if (user.getUsername().isEmpty() ||
                user.getPassword().isEmpty() ||
                user.getRole()==null) {
            return ResponseEntity.badRequest().body(user);
        }
        user = userService.save(user);

        return ResponseEntity
                .created(uriComponentsBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri())
                .body(user);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User newUser,
                                        @PathVariable long id) {
        User updatedUser = userService.getUserById(id)
                .map(user -> {
                    user.setPassword(newUser.getPassword());
                    user.setRole(newUser.getRole());
                    return userService.save(user);
                }).orElseThrow(() -> new UserNotFoundException(id));

        EntityModel<User> entityModel = userModelAssembler.toModel(updatedUser);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(updatedUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        userService.delete(id);

        return ResponseEntity.noContent().build();
    }

}