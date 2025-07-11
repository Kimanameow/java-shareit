package ru.practicum.shareit.item.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentResponseDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
@Component
public interface CommentMapper {
    @Mapping(target = "userAuthor", source = "user")
    @Mapping(target = "item", source = "item")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "commentDto.text")
    @Mapping(target = "created", source = "localDateTime")
    Comment toComment(CommentDto commentDto, Item item, User user, LocalDateTime localDateTime);

    @Mapping(target = "authorName", expression = "java(comment.getUserAuthor().getName())")
    CommentResponseDto toCommentResponseDto(Comment comment);

}
