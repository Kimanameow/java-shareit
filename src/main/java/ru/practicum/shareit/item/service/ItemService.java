package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemBookingCommentDto;
import ru.practicum.shareit.item.dto.ItemCommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    public ItemDto addNewItem(ItemDto itemDto, Long userId);

    public ItemCommentDto getItemById(Long itemId);

    public ItemDto changeItem(ItemDto itemDto, Long userId, Long itemId);

    public List<ItemBookingCommentDto> getItemsByOwner(Long userId);

    public List<ItemDto> findItemsByDescription(String description);

    CommentDto addComment(Long itemId, CommentDto commentDto);
}
