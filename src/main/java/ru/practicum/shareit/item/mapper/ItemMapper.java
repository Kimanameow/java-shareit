package ru.practicum.shareit.item.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.ValidateException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

@Component
public class ItemMapper {

    public static ItemDto toItemDto(Item item) {
        if (item == null) {
            throw new ValidateException("Unexpected item.");
        }
        return ItemDto.builder().id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .owner(item.getOwner())
                .build();
    }

    public static Item toItem(ItemDto itemDto) {
        if (itemDto == null) {
            throw new ValidateException("Unexpected item.");
        }
        return Item.builder().id(itemDto.getId())
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.isAvailable())
                .owner(itemDto.getOwner())
                .build();
    }
}
