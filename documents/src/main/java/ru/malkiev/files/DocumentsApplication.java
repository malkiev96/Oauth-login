package ru.malkiev.files;

import java.security.Principal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.malkiev.files.config.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties(FileStorageProperties.class)
public class DocumentsApplication {

  public static void main(String[] args) {
    SpringApplication.run(DocumentsApplication.class, args);
  }

  @RestController
  public static class HelloController {

    @GetMapping("/hello")
    public Principal hello(Principal principal) {
      return principal;
    }
  }

}
