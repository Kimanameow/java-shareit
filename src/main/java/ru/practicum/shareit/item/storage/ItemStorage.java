package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemStorage {
    public Item addNewItem(Item item, long userId);

    public Item getItemById(long id);

    public Item changeItem(Item item, long id, long itemId);

    public List<ItemDto> getItemsByOwner(long userId);

    public List<ItemDto> findItemsByDescription(String description);
}
