package ru.malkiev.documents.repository;

import org.springframework.data.repository.CrudRepository;
import ru.malkiev.documents.entity.Document;

public interface DocumentRepository extends CrudRepository<Document, Long> {

}
