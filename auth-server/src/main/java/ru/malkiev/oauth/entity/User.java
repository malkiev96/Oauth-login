package ru.malkiev.oauth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
@Table
@Data
@EqualsAndHashCode(of = {"id", "username"})
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private boolean enabled;

    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Role role;

    @Override
    @JsonIgnore
    public List<Role> getAuthorities() {
        return Collections.singletonList(role);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

}