package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.booking.model.Booking;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByBookerId(Long ownerId);

    List<Booking> findAllByItemIdIn(List<Long> itemsId);

    List<Booking> findAllByItemId(Long itemId);
}
