package ru.practicum.shareit.item.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.ValidateException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.storage.UserStorage;

@Component
public class ItemMapper implements ItemMapperInterface {

    private long id = 0;
    UserStorage userStorage;

    @Autowired
    public ItemMapper(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public ItemDto toItemDto(Item item) {
        if (item == null) {
            throw new ValidateException("Unexpected");
        }
        return ItemDto.builder()
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .owner(item.getOwner())
                .build();
    }

    @Override
    public Item toItem(Item item, long userId) {
        if (item == null) {
            throw new ValidateException("Unexpected");
        }
        id++;
        return Item.builder().id(id)
                .description(item.getDescription())
                .name(item.getName())
                .owner(userStorage.getUserById(userId))
                .available(item.getAvailable())
                .build();
    }
}
