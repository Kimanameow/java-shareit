package ru.practicum.shareit.item.dto;

import lombok.Data;

import java.util.List;

@Data
public class ItemCommentDto {
    private long id;
    private String name;
    private String description;
    private Boolean available;
    private String owner;
    private List<CommentResponseDto> comments;
}
