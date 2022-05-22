package ru.malkiev.files.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class StorageException extends RuntimeException {

  public StorageException(String message) {
    super(message);
  }

  public StorageException(Throwable throwable) {
    super(throwable);
  }

}
