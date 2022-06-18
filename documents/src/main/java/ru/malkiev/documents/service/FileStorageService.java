package ru.malkiev.documents.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.malkiev.documents.config.FileStorageProperties;
import ru.malkiev.documents.exception.StorageException;

@Service
public class FileStorageService {

  private final Path uploadDir;

  public FileStorageService(FileStorageProperties properties) {
    this.uploadDir = Paths.get(properties.getUploadDir());
  }

  @SneakyThrows
  @PostConstruct
  public void init() {
    Files.createDirectories(uploadDir);
  }

  public Path saveFile(MultipartFile file, String filename, String... context) {
    if (file.isEmpty()) {
      throw new StorageException("File is empty: " + filename);
    }
    Path directory = uploadDir;
    for (String c : context) {
      directory = directory.resolve(c);
    }
    try (InputStream inputStream = file.getInputStream()) {
      Files.createDirectories(directory);
      Path filePath = directory.resolve(filename);
      Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
      return filePath;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
