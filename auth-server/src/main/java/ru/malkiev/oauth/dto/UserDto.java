package ru.malkiev.oauth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
