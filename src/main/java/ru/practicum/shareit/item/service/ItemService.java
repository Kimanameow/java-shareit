package ru.practicum.shareit.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ValidateException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {
    ItemStorage itemStorage;

    @Autowired
    public ItemService(ItemStorage itemStorage) {
        this.itemStorage = itemStorage;
    }

    public Item addNewItem(Item item, long userId) {
        validateNewItem(item);
        return itemStorage.addNewItem(item, userId);
    }

    public Item getItemById(long id) {
        return itemStorage.getItemById(id);
    }

    public Item changeItem(Item item, long id, long itemId) {
        return itemStorage.changeItem(item, id, itemId);
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

    private void validateNewItem(Item item) {
        if (item.getName() == null || item.getName().isEmpty() || item.getName().isBlank()) {
            throw new ValidateException("Unexpected name");
        }
        if (item.getDescription() == null ||
                item.getDescription().isEmpty() || item.getDescription().isBlank()) {
            throw new ValidateException("Unexpected description");
        }
        if (item.getAvailable() == null) {
            throw new ValidateException("Change available");
        }
    }
}
