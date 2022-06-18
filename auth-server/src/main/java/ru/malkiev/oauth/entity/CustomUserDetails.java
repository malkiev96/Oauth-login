package ru.malkiev.oauth.entity;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

  public static CustomUserDetails of(User user) {
    List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
        .map(Role::getCode)
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
    return new CustomUserDetails(
        user.getId(),
        user.getUsername(),
        user.getFirstName(),
        user.getLastname(),
        user.getPassword(),
        user.getEmail(),
        authorities,
        user.isEnabled(),
        user.isLocked()
    );
  }

  private Long id;
  private String username;
  private String firstName;
  private String lastname;
  private String password;
  private String email;
  private List<? extends GrantedAuthority> authorities;
  private boolean enabled;
  private boolean locked;


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
