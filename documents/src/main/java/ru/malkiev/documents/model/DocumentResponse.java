package ru.malkiev.documents.model;

import java.time.ZonedDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.malkiev.documents.entity.Document;

@Data
@NoArgsConstructor
public class DocumentResponse {

  private Long id;
  private String name;
  private String type;
  private String path;
  private ZonedDateTime createdDateTime;

  public static DocumentResponse of(Document document) {
    DocumentResponse response = new DocumentResponse();
    response.setId(document.getId());
    response.setName(document.getFileName());
    response.setType(document.getDocumentType());
    response.setPath(document.getFilePath());
    response.setCreatedDateTime(document.getCreatedDateTime());

    return response;
  }

}
