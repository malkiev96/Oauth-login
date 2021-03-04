package ru.malkiev.oauth.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import ru.malkiev.oauth.entity.User;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserModel extends RepresentationModel<UserModel> {

    private Long id;
    private String username;
    private String displayName;
    private String email;
    private boolean enabled;
    private String role;

    public UserModel(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.displayName = user.getDisplayName();
        this.email = user.getEmail();
        this.enabled = user.isEnabled();
        this.role = user.getRole().getName();
    }
}
