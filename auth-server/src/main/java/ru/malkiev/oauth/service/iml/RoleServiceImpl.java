package ru.malkiev.oauth.service.iml;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.malkiev.oauth.entity.Role;
import ru.malkiev.oauth.repository.RoleRepository;
import ru.malkiev.oauth.service.RoleService;

@Service
@Transactional
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

  private final RoleRepository repository;

  @Override
  public List<Role> getDefaultRoles() {
    return repository.findAllByDefaultRole(true);
  }

}
