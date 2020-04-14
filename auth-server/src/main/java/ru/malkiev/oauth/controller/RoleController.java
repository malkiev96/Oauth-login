package ru.malkiev.oauth.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.malkiev.oauth.entity.Role;
import ru.malkiev.oauth.repository.RoleRepository;

import java.util.List;

@RestController
@AllArgsConstructor
public class RoleController {

    private final RoleRepository roleRepository;

    @GetMapping("/roles")
    public List<Role> allRoles(){
        return roleRepository.findAll();
    }
}
