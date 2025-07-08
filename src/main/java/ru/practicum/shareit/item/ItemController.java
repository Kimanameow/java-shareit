package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ItemDto createItem(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                              @Valid @RequestBody ItemDto item) {
        return itemService.addNewItem(item, userId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                              @RequestBody ItemDto item, @PathVariable Long itemId) {
        return itemService.changeItem(item, userId, itemId);
    }

    @GetMapping("/{itemId}")
    public ItemBookingCommentDto getItemById(@PathVariable long itemId) {
        return itemService.getItemById(itemId);
    }

    @GetMapping
    public List<ItemBookingCommentDto> getItemsByOwner(@RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.getItemsByOwner(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> searchItems(@RequestParam("text") String searchString) {
        return itemService.findItemsByDescription(searchString);
    }

    @PostMapping("{itemId}/comment")
    public CommentResponseDto addComment(@PathVariable Long itemId,
                                         @Valid @RequestBody CommentDto commentDto,
                                         @RequestHeader(value = "X-Sharer-User-Id") Long authorId) {
        return itemService.addComment(itemId, commentDto, authorId);
    }
}