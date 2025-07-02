package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.EmailException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDto> allUser() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toUserDto).toList();
    }

    @Override
    public UserDto addNewUser(UserDto user) {
        if (user == null) {
            throw new NotFoundException("Empty request.");
        }
        checkEmail(user.getEmail());
        User savedUser = userRepository.save(userMapper.toUser(user));
        return userMapper.toUserDto(savedUser);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto changeUserById(UserDto newUserDto, Long id) {
        User oldUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        if (newUserDto.getName() != null && !newUserDto.getName().isBlank()) {
            oldUser.setName(newUserDto.getName());
        }
        if (newUserDto.getEmail() != null && !newUserDto.getEmail().isBlank()) {
            checkEmail(newUserDto.getEmail());
            oldUser.setEmail(newUserDto.getEmail());
        }
        return userMapper.toUserDto(userRepository.save(oldUser));
    }

    @Override
    public UserDto getUserById(Long id) {
        return userMapper.toUserDto(userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found user with ID " + id)));
    }

    private void checkEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailException("Email is already exist");
        }
    }
}
