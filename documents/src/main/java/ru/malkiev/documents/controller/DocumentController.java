package ru.malkiev.documents.controller;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.malkiev.documents.entity.Document;
import ru.malkiev.documents.model.DocumentResponse;
import ru.malkiev.documents.service.DocumentService;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/documents")
public class DocumentController {

  private final DocumentService service;

  @PostMapping
  public ResponseEntity<DocumentResponse> save(@RequestParam("file") MultipartFile file) {
    Document document = service.save(file);
    return ResponseEntity.ok(DocumentResponse.of(document));
  }

  @GetMapping("/{id}/download")
  public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
    Resource resource = service.loadAsResource(id);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + resource.getFilename() + "\"")
        .body(resource);
  }

  @GetMapping("/{id}")
  public ResponseEntity<DocumentResponse> documentInfo(@PathVariable Long id) {
    return ResponseEntity.ok(
        DocumentResponse.of(service.getById(id))
    );
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<DocumentResponse>> userDocuments(
      @PathVariable Long userId,
      @SortDefault("createdDateTime") Sort sort) {
    return ResponseEntity.ok(
        service.getDocumentsByUserId(userId, sort).stream()
            .map(DocumentResponse::of)
            .collect(Collectors.toList())
    );
  }

}
