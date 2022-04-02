package ru.malkiev.oauth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "AUTH_USER")
public class User {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "USERNAME", nullable = false, unique = true)
  @NotNull
  @Size(min = 3, max = 32)
  private String username;

  @Column(name = "FIRSTNAME", nullable = false)
  @NotNull
  @Size(min = 1, max = 128)
  private String firstName;

  @Column(name = "LASTNAME", nullable = false)
  @NotNull
  @Size(min = 1, max = 128)
  private String lastname;

  @Column(name = "PASSWORD", nullable = false)
  @NotNull
  @JsonIgnore
  private String password;

  @Column(name = "EMAIL", nullable = false, unique = true)
  @NotNull
  @Email
  private String email;

  @Column(name = "ENABLED_FLG", nullable = false)
  private boolean enabled;

  @Column(name = "LOCKED_FLG", nullable = false)
  private boolean locked;

  @ManyToMany
  @JoinTable(
      name = "AUTH_USER_ROLES",
      joinColumns = @JoinColumn(name = "USER_ID"),
      inverseJoinColumns = @JoinColumn(name = "ROLE_ID")
  )
  private Set<Role> roles = new LinkedHashSet<>();

}