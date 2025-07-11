package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.*;

import java.util.List;

public interface ItemService {
    ItemDto addNewItem(ItemDto itemDto, Long userId);

    ItemFullDto getItemById(Long itemId);

    ItemDto changeItem(ItemDto itemDto, Long userId, Long itemId);

    List<ItemFullDto> getItemsByOwner(Long userId);

    List<ItemDto> findItemsByDescription(String description);

    CommentResponseDto addComment(Long itemId, CommentDto commentDto, Long userId);
}
