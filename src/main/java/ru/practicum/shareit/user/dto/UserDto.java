package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private long id;
    @NotBlank(message = "Please, write your name")
    private String name;
    @NotBlank(message = "Email cannot be empty or null")
    @Email(message = "Email should be valid")
    private String email;
}
