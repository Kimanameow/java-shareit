package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.User;

import java.util.List;

public interface ItemStorage {
    public ItemDto addNewItem(ItemDto item, User user);

    public ItemDto getItemById(long itemId);

    public ItemDto changeItem(ItemDto item, long userId, long itemId);

    public List<ItemDto> getItemsByOwner(long userId);

    public List<ItemDto> findItemsByDescription(String description);
}
