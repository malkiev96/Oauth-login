package ru.malkiev.oauth.application.service.iml;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.malkiev.oauth.application.repository.RoleRepository;
import ru.malkiev.oauth.application.service.RoleService;
import ru.malkiev.oauth.domain.Role;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

  private final RoleRepository repository;

  @Override
  public List<Role> getDefaultRoles() {
    return repository.findAllByDefaultRole(true);
  }

}
