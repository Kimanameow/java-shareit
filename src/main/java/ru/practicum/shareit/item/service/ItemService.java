package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.*;

import java.util.List;

public interface ItemService {
    public ItemDto addNewItem(ItemDto itemDto, Long userId);

    public ItemBookingCommentDto getItemById(Long itemId);

    public ItemDto changeItem(ItemDto itemDto, Long userId, Long itemId);

    public List<ItemBookingCommentDto> getItemsByOwner(Long userId);

    public List<ItemDto> findItemsByDescription(String description);

    CommentResponseDto addComment(Long itemId, CommentDto commentDto, Long userId);
}
