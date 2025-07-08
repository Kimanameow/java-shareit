package ru.practicum.shareit.item.dto;

import lombok.Data;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.user.User;

import java.util.List;

@Data
public class ItemCommentDto {
    private long id;
    private String name;
    private String description;
    private Boolean available;
    private User owner;
    private List<Comment> comments;
}
