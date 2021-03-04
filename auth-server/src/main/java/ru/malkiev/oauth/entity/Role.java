package ru.malkiev.oauth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "AUTH_ROLE")
@NoArgsConstructor
@AllArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CODE", nullable = false)
    @Enumerated(value = EnumType.STRING)
    @NotNull
    @JsonIgnore
    private Code code;

    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;

    @Override
    public String getAuthority() {
        return code.name();
    }

    public enum Code {
        ROLE_USER,
        ROLE_ADMIN
    }
}