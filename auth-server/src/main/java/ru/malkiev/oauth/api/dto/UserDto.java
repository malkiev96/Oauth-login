package ru.malkiev.oauth.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {

  @NotNull
  @Size(min = 3, max = 32)
  private String username;

  @NotNull
  @Size(min = 1, max = 128)
  private String firstname;

  @NotNull
  @Size(min = 1, max = 128)
  private String lastname;

  @NotNull
  @Size(min = 6, max = 32)
  private String password;

  @Email
  @NotNull
  private String email;
}
