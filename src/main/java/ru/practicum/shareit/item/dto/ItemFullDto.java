package ru.practicum.shareit.item.dto;

import lombok.Data;
import ru.practicum.shareit.booking.model.Booking;

import java.util.List;

@Data
public class ItemFullDto {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private String owner;
    private List<CommentResponseDto> comments;
    private Booking lastBooking;
    private Booking nextBooking;
}
