package ru.malkiev.documents.repository;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import ru.malkiev.documents.entity.Document;

public interface DocumentRepository extends CrudRepository<Document, Long> {

  List<Document> findAllByUserId(Long userId, Sort sort);

}
