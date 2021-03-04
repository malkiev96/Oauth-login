package ru.malkiev.oauth.assembler;

import lombok.var;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import ru.malkiev.oauth.controller.RoleController;
import ru.malkiev.oauth.entity.Role;
import ru.malkiev.oauth.model.RoleModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RoleModelAssembler implements RepresentationModelAssembler<Role, RoleModel> {

    @Override
    public RoleModel toModel(Role role) {
        var model = new RoleModel(role);
        model
                .add(linkTo(methodOn(RoleController.class).getRole(role.getId())).withRel("role"))
                .add(linkTo(methodOn(RoleController.class).getRoles()).withRel("roles"));

        return model;
    }
}
