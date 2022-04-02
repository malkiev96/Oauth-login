package ru.malkiev.oauth.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.malkiev.oauth.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

  @Query(value = "select u from User u left join fetch u.roles where upper(u.username) = upper(?1) ")
  Optional<User> findByUsername(String username);

  @Query(value = "select u from User u left join fetch u.roles where upper(u.email) = upper(?1) ")
  Optional<User> findByEmail(String email);

}
