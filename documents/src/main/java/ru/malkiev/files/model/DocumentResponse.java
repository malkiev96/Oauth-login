package ru.malkiev.files.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.malkiev.files.entity.Document;

@Data
@NoArgsConstructor
public class DocumentResponse {

  private Long id;
  private String name;
  private String type;

  public static DocumentResponse of(Document document) {
    DocumentResponse response = new DocumentResponse();
    response.setId(document.getId());
    response.setName(document.getFileName());
    response.setType(document.getDocumentType());

    return response;
  }

}
