package ru.malkiev.oauth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Data
@EqualsAndHashCode(of = {"id", "name"})
public class Role implements GrantedAuthority {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@OneToMany(mappedBy = "role")
	@JsonIgnore
	private List<User> users;

	@Override
	@JsonIgnore
	public String getAuthority() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
}