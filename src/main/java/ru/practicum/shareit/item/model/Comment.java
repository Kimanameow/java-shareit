package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import ru.practicum.shareit.user.User;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    Long id;
    @NotBlank(message = "Comment can't be empty")
    String text;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User userAuthor;
    @ManyToOne
    @JoinColumn(name = "item_id")
    Item item;
}
