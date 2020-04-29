package ru.malkiev.oauth.controller;

import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.malkiev.oauth.exception.RoleNotFoundException;
import ru.malkiev.oauth.assembler.RoleModelAssembler;
import ru.malkiev.oauth.entity.Role;
import ru.malkiev.oauth.service.RoleService;

@RestController
@AllArgsConstructor
public class RoleController {

    private final RoleService roleService;
    private final RoleModelAssembler roleModelAssembler;

    @GetMapping("/roles")
    public CollectionModel<EntityModel<Role>> getAllRoles(){
        return roleModelAssembler.toCollectionModel(roleService.getAllRoles());
    }

    @GetMapping("/roles/{id}")
    public EntityModel<Role> getRole(@PathVariable long id) {
        Role role = roleService.getRoleById(id)
                .orElseThrow(() -> new RoleNotFoundException(id));
        return roleModelAssembler.toModel(role);
    }
}
