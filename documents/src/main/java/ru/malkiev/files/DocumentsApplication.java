package ru.malkiev.files;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.malkiev.files.config.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties(FileStorageProperties.class)
public class DocumentsApplication {

  public static void main(String[] args) {
    SpringApplication.run(DocumentsApplication.class, args);
  }

}
