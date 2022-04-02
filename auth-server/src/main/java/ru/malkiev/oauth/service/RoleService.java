package ru.malkiev.oauth.service;

import java.util.List;
import ru.malkiev.oauth.entity.Role;

public interface RoleService {

  List<Role> getDefaultRoles();

}
