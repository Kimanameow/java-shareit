package ru.practicum.shareit.item.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryItemStorage implements ItemStorage {

    private final Map<Long, Item> items = new HashMap<>();
    ItemMapper itemMapper;

    @Autowired
    public InMemoryItemStorage(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    @Override
    public Item addNewItem(Item item, long userId) {
        Item newItem = itemMapper.toItem(item, userId);
        items.put(newItem.getId(), newItem);
        return newItem;
    }

    @Override
    public Item getItemById(long id) {
        checkItemInMap(id);
        return items.get(id);
    }

    @Override
    public Item changeItem(Item newItem, long id, long itemId) {
        checkItemInMap(itemId);
        if (items.get(itemId).getOwner().getId().equals(id)) {
            Item oldItem = items.get(itemId);
            if (newItem.getName() == null || newItem.getName().isBlank()) {
                newItem.setName(oldItem.getName());
            }
            if (newItem.getDescription() == null || newItem.getDescription().isBlank()) {
                newItem.setDescription(oldItem.getDescription());
            }
            return itemMapper.toItem(newItem, id);
        } else throw new NotFoundException("Yoy can't change this item.");
    }

    @Override
    public List<ItemDto> getItemsByOwner(long userId) {
        List<ItemDto> listItems = items.values().stream()
                .filter(item -> item.getOwner().getId() == userId)
                .map(itemMapper::toItemDto)
                .toList();
        if (listItems.isEmpty()) {
            throw new NotFoundException("Not found");
        }
        return listItems;
    }

    @Override
    public List<ItemDto> findItemsByDescription(String description) {
        List<ItemDto> listItems = items.values().stream()
                .filter(item -> item.getName().toLowerCase().contains(description.toLowerCase())
                        || item.getDescription().toLowerCase().contains(description.toLowerCase()))
                .filter(item -> Boolean.TRUE.equals(item.getAvailable()))
                .map(itemMapper::toItemDto)
                .toList();
        return listItems;
    }

    private void checkItemInMap(Long id) {
        if (!items.containsKey(id)) {
            throw new NotFoundException("Not found");
        }
    }
}
