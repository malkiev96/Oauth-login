package ru.malkiev.oauth.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(long id) {
        super("User not found by id: " + id);
    }
}
