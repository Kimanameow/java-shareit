package ru.practicum.shareit.user.storage;

import ru.practicum.shareit.user.User;

import java.util.List;

public interface UserStorage {

    public List<User> allUser();

    public User addNewUser(User user);

    public void deleteUserById(long id);

    public User changeUserById(User user, long id);

    public User getUserById(long id);
}

