package ru.practicum.shareit.user.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.EmailException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidateException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.mapper.UserMapperInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    UserMapperInterface userMapper;

    @Autowired
    public InMemoryUserStorage(UserMapperInterface userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<User> allUser() {
        if (users.isEmpty()) {
            throw new NotFoundException("Not found");
        }
        return users.values().stream().toList();
    }

    @Override
    public User addNewUser(User user) {
        findRepeatingEmail(user.getEmail());
        User newUser = userMapper.newUser(user);
        users.put(newUser.getId(), newUser);
        return newUser;
    }

    @Override
    public void deleteUserById(long id) {
        checkUserById(id);
        users.remove(id);
    }


    @Override
    public User changeUserById(User newUser, long id) {
        checkUserById(id);
        User oldUser = users.get(id);
        if (newUser.getName() != null) {
            if (!newUser.getName().isEmpty() && !newUser.getName().isBlank()) {
                oldUser.setName(newUser.getName());
            }
        }
        if (newUser.getEmail() != null) {
            if (!newUser.getEmail().isBlank() && !newUser.getEmail().isEmpty()) {
                findRepeatingEmail(newUser.getEmail());
                if (!newUser.getEmail().contains("@")) {
                    throw new ValidateException("Unexpected email.");
                } else {
                    oldUser.setEmail(newUser.getEmail());
                }
            }
        }
        return oldUser;
    }

    @Override
    public User getUserById(long id) {
        checkUserById(id);
        return users.get(id);
    }

    private void checkUserById(long id) {
        if (users.isEmpty() || !users.containsKey(id)) {
            throw new NotFoundException("Not found ID " + id);
        }
    }

    private void findRepeatingEmail(String email) {
        if (!users.isEmpty()) {
            if (users.values().stream().anyMatch(user -> user.getEmail().equals(email))) {
                throw new EmailException("Email is already in use");
            }
        }
    }
}
