package ru.malkiev.oauth.controller;

import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.malkiev.oauth.assembler.RoleModelAssembler;
import ru.malkiev.oauth.exception.RoleNotFoundException;
import ru.malkiev.oauth.model.RoleModel;
import ru.malkiev.oauth.repository.RoleRepository;

@RestController
@AllArgsConstructor
public class RoleController {

    private final RoleRepository repository;
    private final RoleModelAssembler assembler;

    @GetMapping("/roles")
    public ResponseEntity<CollectionModel<RoleModel>> getRoles() {
        return ResponseEntity.ok(
                assembler.toCollectionModel(
                        repository.findAll()
                )
        );
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<RoleModel> getRole(@PathVariable Long id) {
        return repository.findById(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RoleNotFoundException(id));
    }
}
