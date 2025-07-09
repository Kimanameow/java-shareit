package ru.practicum.shareit.item.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.ItemBookingCommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

@Mapper(componentModel = "spring")
@Component
public interface ItemMapper {

    ItemDto toItemDto(Item item);

    Item toItem(ItemDto itemDto);

    @Mapping(target = "owner", expression = "java(item.getOwner().getName())")
    ItemBookingCommentDto toItemBookingCommentDto(Item item);
}
