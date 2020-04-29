package ru.malkiev.oauth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.malkiev.oauth.exception.RoleNotFoundException;
import ru.malkiev.oauth.exception.UserNotFoundException;

@ControllerAdvice
public class AdviceController extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler({UserNotFoundException.class, RoleNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String entityNotFoundHandler(Exception e){
        return e.getMessage();
    }
}
