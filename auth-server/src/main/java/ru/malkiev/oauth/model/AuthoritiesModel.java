package ru.malkiev.oauth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.malkiev.oauth.entity.Role;
import ru.malkiev.oauth.entity.User;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthoritiesModel {

    private String name;
    private String displayName;
    private String email;
    private List<Role> authorities;

    public AuthoritiesModel(User user) {
        this.name = user.getUsername();
        this.displayName = user.getDisplayName();
        this.email = user.getEmail();
        this.authorities = user.getAuthorities();
    }

}
