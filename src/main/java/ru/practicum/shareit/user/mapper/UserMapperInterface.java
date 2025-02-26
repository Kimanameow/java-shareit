package ru.practicum.shareit.user.mapper;

import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;

public interface UserMapperInterface {
    public UserDto toUserDto(User user);

    public User newUser(User user);
}
