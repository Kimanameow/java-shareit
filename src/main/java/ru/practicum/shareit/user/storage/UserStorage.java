package ru.practicum.shareit.user.storage;

import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserStorage {

    List<UserDto> allUser();

    UserDto addNewUser(User user);

    void deleteUserById(long id);

    UserDto changeUserById(User user, long id);

    UserDto getUserById(long id);
}

