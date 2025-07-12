package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> allUser();

    UserDto addNewUser(UserDto user);

    void deleteUserById(Long id);

    UserDto changeUserById(UserDto newUser, Long id);

    UserDto getUserById(Long id);
}
