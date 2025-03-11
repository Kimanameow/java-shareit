package ru.practicum.shareit.item.storage;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryItemStorage implements ItemStorage {

    private final Map<Long, Item> items = new HashMap<>();
    private long id = 0;

    @Override
    public ItemDto addNewItem(ItemDto itemDto, User user) {
        itemDto.setId(id);
        itemDto.setOwner(user);
        items.put(id, ItemMapper.toItem(itemDto));
        id++;
        return itemDto;
    }

    @Override
    public ItemDto getItemById(long itemId) {
        checkItemInMap(itemId);
        return ItemMapper.toItemDto(items.get(itemId));
    }

    @Override
    public ItemDto changeItem(ItemDto newItem, long userId, long itemId) {
        checkItemInMap(itemId);
        Item oldItem = items.get(itemId);
        if (oldItem.getOwner().getId() != userId) {
            throw new NotFoundException("You can't change this item");
        }
        if (newItem.getName() == null || newItem.getName().isBlank()) {
            newItem.setName(oldItem.getName());
        }
        if (newItem.getDescription() == null || newItem.getDescription().isBlank()) {
            newItem.setDescription(oldItem.getDescription());
        }
        if (newItem.getAvailable() == null) {
            newItem.setAvailable(oldItem.getAvailable());
        }
        items.put(itemId, ItemMapper.toItem(newItem));
        return newItem;
    }

    @Override
    public List<ItemDto> getItemsByOwner(long userId) {
        return items.values().stream()
                .filter(item -> item.getOwner() != null)
                .filter(item -> item.getOwner().getId() == userId)
                .map(ItemMapper::toItemDto)
                .toList();
    }

    @Override
    public List<ItemDto> findItemsByDescription(String description) {
        List<ItemDto> listItems = items.values().stream()
                .filter(item -> item.getName().toLowerCase().contains(description.toLowerCase())
                        || item.getDescription().toLowerCase().contains(description.toLowerCase()))
                .filter(item -> Boolean.TRUE.equals(item.getAvailable()))
                .map(ItemMapper::toItemDto)
                .toList();
        return listItems;
    }

    private void checkItemInMap(Long itemId) {
        if (!items.containsKey(itemId)) {
            throw new NotFoundException("Not found item with that ID.");
        }
    }
}
