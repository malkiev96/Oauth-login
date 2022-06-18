package ru.malkiev.documents.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DocumentNotFoundException extends RuntimeException {

  public DocumentNotFoundException(Long id) {
    super("Document not found by id: " + id);
  }

}
