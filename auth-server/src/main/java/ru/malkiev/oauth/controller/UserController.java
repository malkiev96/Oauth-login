package ru.malkiev.oauth.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ru.malkiev.oauth.entity.Role;
import ru.malkiev.oauth.entity.User;
import ru.malkiev.oauth.repository.RoleRepository;
import ru.malkiev.oauth.service.UserService;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    @GetMapping("/users/me")
    public Principal user(Principal principal) {
        return principal;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable long id){
        User user = userService.getUserById(id)
                .orElseThrow(()->new EntityNotFoundException("User not found with id " + id));
        return user;
    }

    @GetMapping("/users/role/{id}")
    public List<User> getAllByRoleId(@PathVariable long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id " + id));
        return new ArrayList<>(role.getUsers());
    }

    @GetMapping("/users/search")
    public List<User> searchByUsername(@RequestParam(value = "username", required = true) String username,
                                       @RequestParam(value = "roleName", defaultValue = "", required = false) String roleName) {
        List<User> users = userService.searchUserByUsername(username);

        if (roleName.isEmpty()) {
            return users;
        }
        Role role = roleRepository.findRoleByName(roleName)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with name " + roleName));
        List<User> usersWithRole = new ArrayList<>();
        for (User user : users) {
            if (user.getRoles().contains(role)) {
                usersWithRole.add(user);
            }
        }
        return usersWithRole;

    }

    /**
     * Create new User
     *
     * @param user                 new user
     * @param uriComponentsBuilder set location header for user
     * @return 201 created code or bad request if username, pass or role is empty
     */
    @PostMapping("/users")
    public ResponseEntity<?> addUser(@RequestBody User user, UriComponentsBuilder uriComponentsBuilder) {
        if (user.getUsername().isEmpty() &&
                user.getPassword().isEmpty() &&
                user.getRoles().isEmpty()) {
            return ResponseEntity.badRequest().body(user);
        }
        user = userService.save(user);

        return ResponseEntity
                .created(uriComponentsBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri())
                .body(user);
    }

    /**
     * Update user password and roles
     *
     * @param newUser              updated user
     * @param id                   of user
     * @param uriComponentsBuilder set location header for user
     * @return 201 created code and updated user
     * @throws EntityNotFoundException if user with id not exists
     */
    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User newUser,
                                        @PathVariable long id,
                                        UriComponentsBuilder uriComponentsBuilder) {
        User updatedUser = userService.getUserById(id)
                .map(user -> {
                    user.setPassword(newUser.getPassword());
                    user.setRoles(newUser.getRoles());
                    return user;
                }).orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));
        updatedUser = userService.save(updatedUser);

        return ResponseEntity
                .created(uriComponentsBuilder.path("/users/{id}").buildAndExpand(id).toUri())
                .body(updatedUser);
    }

    /**
     * Delete user
     *
     * @param id of user
     * @return 204 code No content
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        userService.delete(id);

        return ResponseEntity.noContent().build();
    }

}