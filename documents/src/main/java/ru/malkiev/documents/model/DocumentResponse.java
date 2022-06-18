package ru.malkiev.documents.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.malkiev.documents.entity.Document;

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
