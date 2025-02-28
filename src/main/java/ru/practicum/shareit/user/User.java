package ru.practicum.shareit.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private long id;
    @NotNull(message = "Please, write your name")
    private String name;
    @NotBlank(message = "Email cannot be empty or null")
    @Email(message = "Email should be valid")
    private String email;
}
