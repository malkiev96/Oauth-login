package ru.malkiev.oauth.application.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import ru.malkiev.oauth.domain.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

  List<Role> findAllByDefaultRole(Boolean defaultRole);

}
