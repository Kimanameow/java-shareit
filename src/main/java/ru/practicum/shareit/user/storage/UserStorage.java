package ru.practicum.shareit.user.storage;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserStorage {

    List<UserDto> allUser();

    UserDto addNewUser(UserDto user);

    void deleteUserById(long id);

    UserDto changeUserById(UserDto user, long id);

    UserDto getUserById(long id);
}

