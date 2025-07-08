package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.State;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequest;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidateException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public BookingDto createBooking(Long userId, BookingRequest bookingRequest) {
        Booking booking = new Booking();
        booking.setStart(bookingRequest.getStart());
        booking.setEnd(bookingRequest.getEnd());
        booking.setItem(itemRepository.findById(bookingRequest.getItemId())
                .orElseThrow(()-> new NotFoundException("Can't find this item.")));
        booking.setBooker(userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Can't find this user")));
        booking.setStatus(Status.WAITING);
        validateBooking(booking);
        bookingRepository.save(booking);
        return bookingMapper.toBookingDto(booking);
    }

    @Override
    public BookingDto validBooking(Long userId, Long bookingId, boolean approved) {
        validateUser(userId);
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Can't find this booking."));

        if (booking.getItem().getOwner().getId().equals(userId)) {
            booking.setStatus((approved) ? Status.APPROVED : Status.REJECTED);
            bookingRepository.save(booking);
            return bookingMapper.toBookingDto(booking);
        } else throw new ValidateException("You can't change this booking");
    }

    @Override
    public BookingDto getBooking(Long userId, Long bookingId) {
        validateUser(userId);
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Can't find this booking"));

        if (booking.getBooker().getId().equals(userId)
                || booking.getItem().getOwner().getId().equals(userId)) {
            return bookingMapper.toBookingDto(booking);
        } else throw new ValidateException("You can't see this booking");
    }

    @Override
    public List<BookingDto> getBookingsForUser(Long userId, State state) {
        validateUser(userId);
        List<Booking> allBookings = bookingRepository.findAllByBookerId(userId);
        return sortListByState(allBookings, state);
    }

    @Override
    public List<BookingDto> getBookingsForUsersItems(Long userId, State state) {
        validateUser(userId);
        List<Item> usersItems = itemRepository.findAllByOwnerId(userId);
        if (usersItems.isEmpty()) {
            throw new NotFoundException("This user don't have items");
        }

        List<Long> itemsIds = new ArrayList<>();
        for (Item i : usersItems) {
            itemsIds.add(i.getId());
        }

        List<Booking> bookingsForUserItems = bookingRepository.findAllByItemIdIn(itemsIds);
        return sortListByState(bookingsForUserItems, state);
    }

    private void validateUser(Long userId) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("Can't find this user");
        }
    }

    private void validateBooking(Booking booking) {
        if (booking.getStart().isAfter(booking.getEnd()) || booking.getStart().isEqual(booking.getEnd())) {
            throw new ValidateException("Can't add booking with that time");
        }
        if(booking.getItem().getAvailable().equals(false)){
            throw new ValidateException("Unavailable item");
        }
    }

    private List<BookingDto> sortListByState(List<Booking> allBookings, State state) {
        switch (state) {
            case ALL -> {
                return allBookings.stream().map(bookingMapper::toBookingDto).toList();
            }
            case PAST -> {
                return allBookings.stream().filter(x -> x.getEnd().isBefore(LocalDateTime.now()))
                        .sorted(Comparator.comparing(Booking::getStart))
                        .map(bookingMapper::toBookingDto).toList();
            }
            case FUTURE -> {
                return allBookings.stream().filter(x -> x.getStart().isAfter(LocalDateTime.now()))
                        .sorted(Comparator.comparing(Booking::getStart))
                        .map(bookingMapper::toBookingDto).toList();
            }
            case CURRENT -> {
                return allBookings.stream().filter(x -> x.getStart().isBefore(LocalDateTime.now()))
                        .filter(x -> x.getEnd().isAfter(LocalDateTime.now()))
                        .sorted(Comparator.comparing(Booking::getStart))
                        .map(bookingMapper::toBookingDto).toList();
            }
            case WAITING -> {
                return allBookings.stream().filter(x -> x.getStatus().equals(Status.WAITING))
                        .map(bookingMapper::toBookingDto).toList();
            }
            case REJECTED -> {
                return allBookings.stream().filter(x -> x.getStatus().equals(Status.REJECTED))
                        .map(bookingMapper::toBookingDto).toList();
            }
            default -> throw new ValidateException("Unexpected state");
        }
    }
}
