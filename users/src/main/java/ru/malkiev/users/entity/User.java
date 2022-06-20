package ru.malkiev.users.entity;

import java.util.LinkedList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SUB_USER")
public class User {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "USERNAME", nullable = false, unique = true)
  private String username;

  @ManyToMany
  @JoinTable(name = "SUB_USER_SUBSCRIBER")
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private List<User> friends = new LinkedList<>();

}
