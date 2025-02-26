package ru.practicum.shareit.user.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.ValidateException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;

@Component
public class UserMapper implements UserMapperInterface {

    private long id = 0;

    @Override
    public UserDto toUserDto(User user) {
        if (user == null) {
            throw new ValidateException("Unexpected");
        }
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        return userDto;
    }

    @Override
    public User newUser(User user) {
        if (user == null) {
            throw new ValidateException("Unexpected");
        }
        id++;
        return User.builder().name(user.getName()).email(user.getEmail()).id(id).build();
    }
}
