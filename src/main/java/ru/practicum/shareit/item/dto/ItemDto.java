package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.user.User;

@Data
public class ItemDto {
    private long id;
    @NotBlank(message = "Name can't be empty")
    private String name;
    @NotBlank(message = "Please, write a description")
    private String description;
    @NotNull(message = "Write available")
    private Boolean available;
    private User owner;
}
