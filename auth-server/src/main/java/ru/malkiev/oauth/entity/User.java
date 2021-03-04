package ru.malkiev.oauth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;

@Data
@Entity
@Table(name = "AUTH_USER")
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USERNAME", nullable = false, unique = true)
    @NotNull
    @Size(min = 3, max = 32)
    private String username;

    @Column(name = "DISPLAY_NAME", nullable = false)
    @NotNull
    @Size(min = 3, max = 64)
    private String displayName;

    @Column(name = "PASSWORD", nullable = false)
    @NotNull
    @JsonIgnore
    private String password;

    @Column(name = "EMAIL", nullable = false, unique = true)
    @NotNull
    @Email
    private String email;

    @Column(name = "ENABLED", nullable = false)
    private boolean enabled;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID", nullable = false)
    @NotNull
    private Role role;

    @Override
    public List<Role> getAuthorities() {
        return Collections.singletonList(role);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

}