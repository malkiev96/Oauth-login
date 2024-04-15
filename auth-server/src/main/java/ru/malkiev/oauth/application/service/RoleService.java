package ru.malkiev.oauth.application.service;

import java.util.List;
import ru.malkiev.oauth.domain.Role;

public interface RoleService {

  List<Role> getDefaultRoles();

}
