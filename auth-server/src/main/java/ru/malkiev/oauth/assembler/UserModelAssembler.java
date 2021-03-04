package ru.malkiev.oauth.assembler;

import lombok.var;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import ru.malkiev.oauth.controller.UserController;
import ru.malkiev.oauth.entity.User;
import ru.malkiev.oauth.model.UserModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, UserModel> {

    @Override
    public UserModel toModel(User user) {
        var model = new UserModel(user);
        model
                .add(linkTo(methodOn(UserController.class).getUser(user.getId())).withSelfRel())
                .add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"));
        return model;
    }
}
