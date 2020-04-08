package ru.malkiev.oauth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.malkiev.oauth.entity.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
	Optional<Role> findRoleByName(String name);
	List<Role> findAll();
}
