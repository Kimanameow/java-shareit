package ru.practicum.shareit.user.mapper;


import ru.practicum.shareit.exception.ValidateException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;

public class UserMapper {

    public static UserDto toUserDto(User user) {
        if (user == null) {
            throw new ValidateException("Void request.");
        }
        return UserDto.builder().id(user.getId()).email(user.getEmail()).name(user.getName()).build();
    }

    public static User toUser(UserDto userDto) {
        if (userDto == null) {
            throw new ValidateException("Void request.");
        }
        return User.builder().id(userDto.getId()).email(userDto.getEmail())
                .name(userDto.getName()).build();
    }
}
