package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.State;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequest;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public BookingDto createBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                    @RequestBody BookingRequest bookingRequest) {
        return bookingService.createBooking(userId, bookingRequest);
    }

    @PatchMapping(path = "/{bookingId}")
    public BookingDto validBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                             @PathVariable Long bookingId, @RequestParam boolean approved) {
        return bookingService.validBooking(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public BookingDto getBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                 @PathVariable Long bookingId) {
        return bookingService.getBooking(userId, bookingId);
    }

    @GetMapping
    public List<BookingDto> getBookingsForUser(@RequestHeader("X-Sharer-User-Id") long userId,
                                               @RequestParam(defaultValue = "ALL") State state) {
        return bookingService.getBookingsForUser(userId, state);
    }

    @GetMapping("/owner")
    public List<BookingDto> getBookingsForUsersItems(@RequestHeader("X-Sharer-User-Id") long userId,
                                                     @RequestParam (defaultValue = "ALL") State state) {
        return bookingService.getBookingsForUsersItems(userId, state);
    }
}
