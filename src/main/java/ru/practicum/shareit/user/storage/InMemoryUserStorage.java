package ru.practicum.shareit.user.storage;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.EmailException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidateException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    long id = 0;

    @Override
    public List<UserDto> allUser() {
        if (users.isEmpty()) {
            throw new NotFoundException("Not found");
        }
        return users.values().stream()
                .map(UserMapper::toUserDto)
                .toList();
    }

    @Override
    public UserDto addNewUser(User user) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            throw new ValidateException("Validate exception");
        }
        findRepeatingEmail(user.getEmail());
        setId(user);
        users.put(user.getId(), user);
        return UserMapper.toUserDto(user);
    }

    @Override
    public void deleteUserById(long userId) {
        checkUserById(userId);
        users.remove(userId);
    }


    @Override
    public UserDto changeUserById(User newUser, long userId) {
        checkUserById(userId);
        User oldUser = users.get(userId);
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
        return UserMapper.toUserDto(oldUser);
    }

    @Override
    public UserDto getUserById(long userId) {
        checkUserById(userId);
        return UserMapper.toUserDto(users.get(userId));
    }

    private void checkUserById(long userId) {
        if (users.isEmpty() || !users.containsKey(userId)) {
            throw new NotFoundException("Not found ID " + userId);
        }
    }

    private void findRepeatingEmail(String email) {
        if (!users.isEmpty()) {
            if (users.values().stream().anyMatch(user -> user.getEmail().equals(email))) {
                throw new EmailException("Email is already in use");
            }
        }
    }

    private void setId(User user) {
        id++;
        user.setId(id);
    }
}
