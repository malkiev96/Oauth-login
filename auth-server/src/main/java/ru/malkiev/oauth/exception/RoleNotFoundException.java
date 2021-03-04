package ru.malkiev.oauth.exception;

import ru.malkiev.oauth.entity.Role;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(long id) {
        super("Role not found by id: " + id);
    }

    public RoleNotFoundException(Role.Code code) {
        super("Role not found by code: " + code);
    }
}
