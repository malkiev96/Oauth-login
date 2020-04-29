package ru.malkiev.oauth.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.malkiev.oauth.entity.Role;
import ru.malkiev.oauth.entity.User;
import ru.malkiev.oauth.repository.RoleRepository;
import ru.malkiev.oauth.repository.UserRepository;

import java.util.*;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getUsersByRoleName(String name) {
        Optional<Role> role = roleRepository.findRoleByName(name);

        return role.isPresent()
                ? new ArrayList<>(role.get().getUsers())
                : Collections.emptyList();
    }

    @Override
    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        return userRepository.save(user);
    }

    @Override
    public List<User> searchUserByUsername(String username) {
        return userRepository.findAllByUsernameContains(username);
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }
}
