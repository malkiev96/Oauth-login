package ru.malkiev.oauth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "AUTH_ROLE")
@NoArgsConstructor
@AllArgsConstructor
public class Role {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "CODE", nullable = false, unique = true)
  @NotNull
  private String code;

  @Column(name = "NAME", nullable = false, unique = true)
  @NotNull
  private String name;

  @Column(name = "DEFAULT_FLG", nullable = false)
  private boolean defaultRole;

}