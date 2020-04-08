package ru.malkiev.oauth.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity(name = "roles")
@Data
public class Role implements GrantedAuthority {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@Override
	public String getAuthority() {
		return name;
	}
}