package ru.malkiev.files.repository;

import org.springframework.data.repository.CrudRepository;
import ru.malkiev.files.entity.Document;

public interface DocumentRepository extends CrudRepository<Document, Long> {

}
