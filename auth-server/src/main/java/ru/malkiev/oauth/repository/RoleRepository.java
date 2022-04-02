package ru.malkiev.oauth.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import ru.malkiev.oauth.entity.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

  List<Role> findAllByDefaultRole(Boolean defaultRole);

}
