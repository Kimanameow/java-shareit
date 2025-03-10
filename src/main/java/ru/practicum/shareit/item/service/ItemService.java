package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemStorage itemStorage;
    private final UserStorage userStorage;

    public ItemDto addNewItem(ItemDto item, long userId) {
        User user = UserMapper.toUser(userStorage.getUserById(userId));
        return itemStorage.addNewItem(item, user);
    }

    public ItemDto getItemById(long itemId) {
        return itemStorage.getItemById(itemId);
    }

    public ItemDto changeItem(ItemDto item, long userId, long itemId) {
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
