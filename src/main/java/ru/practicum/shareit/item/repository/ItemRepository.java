package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findAllByOwnerId(long userId);

    List<Item> findAllByNameContainingIgnoreCaseAndAvailableTrue(String description);

    @Query("SELECT i FROM Item i LEFT JOIN FETCH i.comments WHERE i.id = :id")
    Item findItemWithComments(@Param("id") Long id);

    @Query("SELECT i from Item i LEFT JOIN FETCH i.comments WHERE i.owner.id = :id")
    List<Item> findAllByOwnerIdWithComments(@Param("id") Long id);
}
