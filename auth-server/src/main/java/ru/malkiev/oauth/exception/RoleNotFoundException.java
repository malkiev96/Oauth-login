package ru.malkiev.oauth.exception;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(long id) {
        super("Role not found with id: " + id);
    }

    public RoleNotFoundException(String name) {
        super("Role not found with name: " + name);
    }
}
