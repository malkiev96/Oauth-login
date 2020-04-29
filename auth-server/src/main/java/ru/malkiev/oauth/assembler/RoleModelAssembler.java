package ru.malkiev.oauth.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import ru.malkiev.oauth.controller.RoleController;
import ru.malkiev.oauth.controller.UserController;
import ru.malkiev.oauth.entity.Role;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class RoleModelAssembler implements RepresentationModelAssembler<Role, EntityModel<Role>> {

    @Override
    public EntityModel<Role> toModel(Role role) {
        return new EntityModel<>(role,
                linkTo(methodOn(RoleController.class).getRole(role.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).getAllByRoleId(role.getId())).withRel("users"),
                linkTo(methodOn(RoleController.class).getAllRoles()).withRel("roles"));
    }
}
