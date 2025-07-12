package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.State;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequest;

import java.util.List;

public interface BookingService {
    BookingDto createBooking(Long userId, BookingRequest bookingRequest);

    BookingDto validBooking(Long userId, Long bookingId, boolean approved);

    BookingDto getBooking(Long userId, Long bookingId);

    List<BookingDto> getBookingsForUser(Long userId, State state);

    List<BookingDto> getBookingsForUsersItems(Long userId, State state);
}
