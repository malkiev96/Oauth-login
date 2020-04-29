package ru.malkiev.oauth.service;

import ru.malkiev.oauth.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    List<Role> getAllRoles();

    Optional<Role> getRoleById(long id);

    Optional<Role> getRoleByName(String name);

    Role save(Role role);

    void delete(long id);

}
