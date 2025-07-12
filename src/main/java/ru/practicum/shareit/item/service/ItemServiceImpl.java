package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidateException;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final BookingRepository bookingRepository;
    private final CommentMapper commentMapper;

    @Override
    public ItemDto addNewItem(ItemDto itemDto, Long userId) {
        User itemOwner = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with ID" + userId + " not found"));

        Item item = itemMapper.toItem(itemDto);
        item.setOwner(itemOwner);
        return itemMapper.toItemDto(itemRepository.save(item));
    }

    @Override
    public ItemFullDto getItemById(Long itemId) {
        Item item = itemRepository.findItemWithComments(itemId);
        if (item == null) {
            throw new NotFoundException("Item not found");
        }
        return itemMapper.toItemBookingCommentDto(item);
    }

    @Override
    public ItemDto changeItem(ItemDto itemDto, Long userId, Long itemId) {
        Item itemFromDataBase = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Can't found item with ID " + itemId));

        boolean isChanged = false;
        if (Objects.equals(itemFromDataBase.getOwner().getId(), userId)) {
            if (itemDto.getName() != null && !itemDto.getName().isBlank()
                    && !itemDto.getName().equals(itemFromDataBase.getName())) {
                itemFromDataBase.setName(itemDto.getName());
                isChanged = true;
            }
            if (itemDto.getDescription() != null && !itemDto.getDescription().isBlank()
                    && !itemDto.getDescription().equals(itemFromDataBase.getDescription())) {
                itemFromDataBase.setDescription(itemDto.getDescription());
                isChanged = true;
            }
            if (itemDto.getAvailable() != null
                    && !itemDto.getAvailable().equals(itemFromDataBase.getAvailable())) {
                itemFromDataBase.setAvailable(itemDto.getAvailable());
                isChanged = true;
            }
            if (isChanged) {
                itemRepository.save(itemFromDataBase);
            }
            return itemMapper.toItemDto(itemFromDataBase);
        } else throw new NotFoundException("You can't change this item");
    }

    @Override
    public List<ItemFullDto> getItemsByOwner(Long userId) {
        List<ItemFullDto> returnedList = new ArrayList<>();

        List<Item> itemsForUserWithComm = itemRepository.findAllByOwnerIdWithComments(userId);
        if (itemsForUserWithComm.isEmpty()) {
            return new ArrayList<>();
        }

        List<Long> itemsIds = new ArrayList<>();
        for (Item item : itemsForUserWithComm) {
            itemsIds.add(item.getId());
        }

        List<Booking> bookingsForItems = bookingRepository.findAllByItemIdIn(itemsIds);
        for (Item item : itemsForUserWithComm) {
            List<Booking> bookingsForThisItem = bookingsForItems.stream()
                    .filter(booking -> booking.getItem().equals(item)).toList();

            ItemFullDto itemFullDto = itemMapper.toItemBookingCommentDto(item);

            itemFullDto.setLastBooking(bookingsForThisItem.stream()
                    .filter(booking -> booking.getEnd().isBefore(LocalDateTime.now()))
                    .max(Comparator.comparing(Booking::getEnd))
                    .orElse(null));

            itemFullDto.setNextBooking(bookingsForThisItem.stream()
                    .filter(booking -> booking.getStart().isAfter(LocalDateTime.now()))
                    .min(Comparator.comparing(Booking::getStart))
                    .orElse(null));

            returnedList.add(itemFullDto);
        }
        return returnedList;
    }

    @Override
    public List<ItemDto> findItemsByDescription(String description) {
        if (description == null || description.isBlank()) {
            return new ArrayList<>();
        }

        List<Item> foundedItems = itemRepository.findAllByNameContainingIgnoreCaseAndAvailableTrue(description);
        if (foundedItems.isEmpty()) {
            return new ArrayList<>();
        }
        return foundedItems.stream().map(itemMapper::toItemDto).toList();
    }

    @Override
    public CommentResponseDto addComment(Long itemId, CommentDto commentDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Can't find this user"));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Cant find this item"));

        List<Booking> bookingsForUser = bookingRepository.findAllByBookerId(userId);
        bookingsForUser.stream().filter(x -> x.getItem().equals(item)).findFirst()
                .orElseThrow(() -> new ValidateException("Can't find your booking for this item"));

        if (bookingsForUser.stream().noneMatch(b -> b.getStatus() == Status.APPROVED
                && b.getEnd().isBefore(LocalDateTime.now()))) {
            throw new ValidateException("The user has not used the item");
        }

        Comment comment = commentMapper.toComment(commentDto, item, user, LocalDateTime.now());
        return commentMapper.toCommentResponseDto(commentRepository.save(comment));
    }
}
