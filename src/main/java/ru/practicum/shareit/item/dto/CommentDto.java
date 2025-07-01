package ru.practicum.shareit.item.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

@Data
public class CommentDto {
    User author;
    @NotBlank(message = "Comment can't have empty body")
    String text;
    Item item;
}
