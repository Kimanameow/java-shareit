package ru.practicum.shareit.item.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.ItemBookingCommentDto;
import ru.practicum.shareit.item.dto.ItemCommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

@Mapper(componentModel = "spring")
@Component
public interface ItemMapper {

    ItemDto toItemDto(Item item);

    Item toItem(ItemDto itemDto);

    ItemBookingCommentDto toItemBookingDto(Item item);

    ItemCommentDto toItemCommentDto(Item item);

    ItemBookingCommentDto toItemBookingCommentDto(Item item);
}
