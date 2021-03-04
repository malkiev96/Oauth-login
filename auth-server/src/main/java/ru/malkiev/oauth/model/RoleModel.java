package ru.malkiev.oauth.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import ru.malkiev.oauth.entity.Role;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RoleModel extends RepresentationModel<RoleModel> {

    private Long id;
    private String code;
    private String name;

    public RoleModel(Role role) {
        this.id = role.getId();
        this.code = role.getAuthority();
        this.name = role.getName();
    }

}
