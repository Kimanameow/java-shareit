package ru.practicum.shareit.booking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequest;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(target = "booker", source = "booker")
    @Mapping(target = "item", source = "item")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    Booking toBooking(BookingRequest bookingRequest, Item item, User booker);

    BookingDto toBookingDto(Booking booking);
}
