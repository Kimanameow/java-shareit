package ru.practicum.shareit.item.model;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.user.User;

@Data
@Builder
public class Item {
    private long id;
    private String name;
    private String description;
    private Boolean available;
    private User owner;
}
