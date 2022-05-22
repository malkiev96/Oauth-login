package ru.malkiev.files.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.malkiev.files.entity.Document;
import ru.malkiev.files.model.DocumentResponse;
import ru.malkiev.files.service.DocumentService;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/documents")
public class DocumentController {

  private final DocumentService service;

  @PostMapping
  public ResponseEntity<DocumentResponse> save(z@RequestParam("file") MultipartFile file) {
    Document document = service.save(10L, file);
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

  @GetMapping("/{id}/info")
  public ResponseEntity<DocumentResponse> documentInfo(@PathVariable Long id) {
    return ResponseEntity.ok(
        DocumentResponse.of(service.getById(id))
    );
  }

}
