package ru.malkiev.users.repository;

import org.springframework.data.repository.CrudRepository;
import ru.malkiev.users.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

}
