package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemStorage {
    public ItemDto addNewItem(Item item, long userId);

    public ItemDto getItemById(long itemId);

    public ItemDto changeItem(Item item, long userId, long itemId);

    public List<ItemDto> getItemsByOwner(long userId);

    public List<ItemDto> findItemsByDescription(String description);
}
