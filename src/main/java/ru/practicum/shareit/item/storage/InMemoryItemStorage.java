package ru.practicum.shareit.item.storage;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidateException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class InMemoryItemStorage implements ItemStorage {

    private final Map<Long, Item> items = new HashMap<>();
    private final UserStorage userStorage;
    private long id = 0;

    @Autowired
    public InMemoryItemStorage(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public ItemDto addNewItem(Item item, long userId) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Item>> violations = validator.validate(item);
        if (!violations.isEmpty()) {
            throw new ValidateException("Validate exception");
        }
        Item newItem = Item.builder().id(id)
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .owner(UserMapper.toUser(userStorage.getUserById(userId)))
                .build();
        items.put(id, newItem);
        id++;
        return ItemMapper.toItemDto(newItem);
    }

    @Override
    public ItemDto getItemById(long itemId) {
        checkItemInMap(itemId);
        return ItemMapper.toItemDto(items.get(itemId));
    }

    @Override
    public ItemDto changeItem(Item newItem, long userId, long itemId) {
        checkItemInMap(itemId);
        Item oldItem = items.get(itemId);
        if (oldItem.getOwner().getId() == (userId)) {
            if (newItem.getName() == null || newItem.getName().isBlank()) {
                newItem.setName(oldItem.getName());
            }
            if (newItem.getDescription() == null || newItem.getDescription().isBlank()) {
                newItem.setDescription(oldItem.getDescription());
            }
            if (newItem.getAvailable() == null) {
                newItem.setAvailable(oldItem.getAvailable());
            }
            items.put(itemId, newItem);
            return ItemMapper.toItemDto(newItem);
        } else {
            throw new NotFoundException("You can't change this item.");
        }
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
