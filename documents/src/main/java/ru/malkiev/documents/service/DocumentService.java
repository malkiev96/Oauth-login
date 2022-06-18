package ru.malkiev.documents.service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.malkiev.documents.entity.Document;
import ru.malkiev.documents.exception.DocumentNotFoundException;
import ru.malkiev.documents.exception.StorageException;
import ru.malkiev.documents.repository.DocumentRepository;

@Service
@AllArgsConstructor
public class DocumentService {

  private final CurrentUser currentUser;
  private final FileStorageService storageService;
  private final DocumentRepository repository;

  public Document save(MultipartFile file) {
    Long userId = currentUser.getUserId();
    Objects.requireNonNull(file.getOriginalFilename(), "file name can't be null");
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    String randomFolder = UUID.randomUUID().toString();
    Path pathToFile = storageService.saveFile(file, fileName, userId.toString(), randomFolder);

    Document document = new Document();
    document.setUserId(userId);
    document.setDocumentType(file.getContentType());
    document.setFilePath(pathToFile.toString());
    document.setFileName(fileName);
    document.setCreatedDateTime(ZonedDateTime.now());

    return repository.save(document);
  }

  public Document getById(Long documentId) {
    return repository.findById(documentId)
        .orElseThrow(() -> new DocumentNotFoundException(documentId));
  }

  public List<Document> getDocumentsByUserId(Long userId, Sort sort) {
    return repository.findAllByUserId(userId, sort);
  }

  public Resource loadAsResource(Long documentId) {
    Document document = getById(documentId);
    try {
      UrlResource resource = new UrlResource(Paths.get(document.getFilePath()).toUri());
      if (resource.exists()) {
        return resource;
      } else {
        throw new StorageException("File not found:" + document.getFileName());
      }
    } catch (MalformedURLException e) {
      throw new StorageException(e);
    }
  }

}
