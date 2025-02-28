package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;

    public List<UserDto> allUser() {
        return userStorage.allUser();
    }

    public UserDto addNewUser(User user) {
        return userStorage.addNewUser(user);
    }

    public void deleteUserById(long id) {
        userStorage.deleteUserById(id);
    }

    public UserDto changeUserById(User user, long id) {
        return userStorage.changeUserById(user, id);
    }

    public UserDto getUserById(long id) {
        return userStorage.getUserById(id);
    }
}
