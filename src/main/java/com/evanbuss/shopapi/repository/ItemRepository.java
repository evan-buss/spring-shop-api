package com.evanbuss.shopapi.repository;

import com.evanbuss.shopapi.models.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long> {
}
