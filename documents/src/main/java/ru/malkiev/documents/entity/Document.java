package ru.malkiev.documents.entity;

import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.springframework.data.domain.Persistable;

@Data
@Entity
@Table(name = "FILES_DOCUMENT")
public class Document implements Persistable<Long> {

  @Id
  @Column(name = "ID", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "USER_ID", nullable = false)
  private Long userId;

  @Column(name = "FILE_NAME", nullable = false)
  private String fileName;

  @Column(name = "FILE_PATH", nullable = false)
  private String filePath;

  @Column(name = "DOCUMENT_TYPE")
  private String documentType;

  @Column(name = "CREATED_DATE_TIME", nullable = false)
  private ZonedDateTime createdDateTime;

  @Override
  public boolean isNew() {
    return id == null;
  }
}
