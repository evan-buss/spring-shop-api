package com.evanbuss.shopapi.repository;

import com.evanbuss.shopapi.models.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ItemRepository extends CrudRepository<Item, Long> {
  Optional<Item> getItemByListIdAndId(Long listId, Long itemId);
}
