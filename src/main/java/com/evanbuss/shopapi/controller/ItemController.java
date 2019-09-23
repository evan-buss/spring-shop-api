package com.evanbuss.shopapi.controller;

import com.evanbuss.shopapi.message.request.item.CreateItemRequest;
import com.evanbuss.shopapi.models.Item;
import com.evanbuss.shopapi.models.List;
import com.evanbuss.shopapi.repository.ItemRepository;
import com.evanbuss.shopapi.repository.ListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/item")
public class ItemController {

  private ItemRepository itemRepository;
  private ListRepository listRepository;

  @Autowired
  public ItemController(ItemRepository itemRepository, ListRepository listRepository) {
    this.itemRepository = itemRepository;
    this.listRepository = listRepository;
  }

  @GetMapping(params = "list")
  public ResponseEntity<?> getListItems(@RequestParam long list) {
    Optional<List> requestedList = listRepository.findById(list);
    if (requestedList.isPresent()) {
      return ResponseEntity.ok(requestedList.get());
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping(params = {"list", "item"})
  public ResponseEntity<?> getItem(@RequestParam long list, @RequestParam long item) {
    Optional<Item> requestedItem = itemRepository.getItemByListIdAndId(list, item);
    if (requestedItem.isPresent()) {
      return ResponseEntity.ok(requestedItem.get());
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<?> createItem(@Valid @RequestBody CreateItemRequest itemRequest) {
    Optional<List> list = listRepository.findById(itemRequest.getListID());

    if (list.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    Item item = new Item(list.get(), itemRequest.getTitle(), itemRequest.getDescription());
    Item newItem = itemRepository.save(item);

    return ResponseEntity.ok(newItem);
  }

  @DeleteMapping
  public ResponseEntity<?> deleteItem(@RequestParam long list, @RequestParam long item) {
    boolean listExists = listRepository.existsById(list);
    if (!listExists) {
      return ResponseEntity.notFound().build();
    }
    itemRepository.deleteById(item);
    return ResponseEntity.ok().build();
  }
}
