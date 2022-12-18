package ru.malkiev.oauth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;
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