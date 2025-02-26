package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

public interface ItemMapperInterface {
    public ItemDto toItemDto(Item item);

    public Item toItem(Item item, long userId);
}
