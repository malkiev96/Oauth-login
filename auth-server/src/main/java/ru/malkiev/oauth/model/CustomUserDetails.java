package ru.malkiev.oauth.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.malkiev.oauth.entity.User;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {

  public CustomUserDetails(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.firstName = user.getFirstName();
    this.lastName = user.getLastname();
    this.email = user.getEmail();
    this.password = user.getPassword();
    this.enabled = user.isEnabled();
    this.locked = user.isLocked();
    user.getRoles().forEach(r -> this.authorities.add(new SimpleGrantedAuthority(r.getCode())));
  }

  private Long id;
  private String username;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private boolean enabled;
  private boolean locked;

  private List<GrantedAuthority> authorities = new ArrayList<>();

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
