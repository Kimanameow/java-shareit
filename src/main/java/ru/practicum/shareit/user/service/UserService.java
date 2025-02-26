package ru.practicum.shareit.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ValidateException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.List;

@Service
public class UserService {
    UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public List<User> allUser() {
        return userStorage.allUser();
    }

    public User addNewUser(User user) {
        validateUser(user);
        return userStorage.addNewUser(user);
    }

    public void deleteUserById(long id) {
        userStorage.deleteUserById(id);
    }

    public User changeUserById(User user, long id) {
        return userStorage.changeUserById(user, id);
    }

    public User getUserById(long id) {
        return userStorage.getUserById(id);
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new ValidateException("Unexpected");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            throw new ValidateException("Unexpected name");
        }
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidateException("Unexpected Email.");
        }
    }
}
