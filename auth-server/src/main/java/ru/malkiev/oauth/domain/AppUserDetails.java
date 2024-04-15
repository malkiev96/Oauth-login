package ru.malkiev.oauth.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class AppUserDetails implements UserDetails {

  public AppUserDetails(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.firstName = user.getFirstName();
    this.lastName = user.getLastname();
    this.email = user.getEmail();
    this.password = user.getPassword();
    this.enabled = user.isEnabled();
    this.locked = user.isLocked();
    user.getRoles().forEach(role -> this.roles.add(role.getCode()));
  }

  private Long id;
  private String username;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private boolean enabled;
  private boolean locked;

  private List<String> roles = new ArrayList<>();

  @Override
  @JsonIgnore
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles.stream().map(SimpleGrantedAuthority::new).toList();
  }

  @Override
  public boolean isAccountNonExpired() {
    return enabled;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !locked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return enabled;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

}
