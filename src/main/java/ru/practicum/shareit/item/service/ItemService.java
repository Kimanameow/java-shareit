package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemStorage itemStorage;

    public ItemDto addNewItem(Item item, long userId) {
        return itemStorage.addNewItem(item, userId);
    }

    public ItemDto getItemById(long itemId) {
        return itemStorage.getItemById(itemId);
    }

    public ItemDto changeItem(Item item, long userId, long itemId) {
        return itemStorage.changeItem(item, userId, itemId);
    }

    public List<ItemDto> getItemsByOwner(long userId) {
        return itemStorage.getItemsByOwner(userId);
    }

    public List<ItemDto> findItemsByDescription(String description) {
        if (description.isBlank()) {
            return new ArrayList<>();
        }
        return itemStorage.findItemsByDescription(description);
    }
}
