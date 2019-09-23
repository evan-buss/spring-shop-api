package com.evanbuss.shopapi.controller;

import com.evanbuss.shopapi.message.request.item.CreateItemRequest;
import com.evanbuss.shopapi.models.Family;
import com.evanbuss.shopapi.models.Item;
import com.evanbuss.shopapi.models.List;
import com.evanbuss.shopapi.models.User;
import com.evanbuss.shopapi.repository.ItemRepository;
import com.evanbuss.shopapi.repository.ListRepository;
import com.evanbuss.shopapi.security.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
  public ResponseEntity<?> getListItems(@RequestParam long list, Authentication authentication) {
    Optional<List> requestedList = listRepository.findById(list);
    if (requestedList.isPresent()
        && userIsMemberOfFamily(requestedList.get().getFamily(), authentication)) {
      return ResponseEntity.ok(requestedList.get());
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping(params = {"list", "item"})
  public ResponseEntity<?> getItem(
      @RequestParam long list, @RequestParam long item, Authentication authentication) {

    Optional<Item> requestedItem = itemRepository.getItemByListIdAndId(list, item);
    if (requestedItem.isPresent()
        && userIsMemberOfFamily(requestedItem.get().getList().getFamily(), authentication)) {
      return ResponseEntity.ok(requestedItem.get());
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<?> createItem(
      @Valid @RequestBody CreateItemRequest itemRequest, Authentication authentication) {

    User user = ((UserPrinciple) authentication.getPrincipal()).getUser();

    Optional<List> list = listRepository.findById(itemRequest.getListID());

    if (list.isEmpty() || !userIsMemberOfFamily(list.get().getFamily(), authentication)) {
      return ResponseEntity.notFound().build();
    }

    Item item = new Item(user, list.get(), itemRequest.getTitle(), itemRequest.getDescription());
    Item newItem = itemRepository.save(item);

    return ResponseEntity.ok(newItem);
  }

  @DeleteMapping
  public ResponseEntity<?> deleteItem(
      @RequestParam long list, @RequestParam long item, Authentication authentication) {

    User user = ((UserPrinciple) authentication.getPrincipal()).getUser();
    boolean listExists = listRepository.existsById(list);
    Optional<Item> foundItem = itemRepository.findById(item);

    if (!listExists) {
      return ResponseEntity.badRequest().body("WTF");
    }

    if (foundItem.isEmpty() || !foundItem.get().getCreator().getId().equals(user.getId())) {
      return ResponseEntity.badRequest().body("Authorization error bub");
    }

    itemRepository.deleteById(item);
    return ResponseEntity.ok().build();
  }

  /**
   * Check if the user making the request belongs to {@link Family}
   *
   * @param family
   * @param authentication
   * @return
   */
  private boolean userIsMemberOfFamily(Family family, Authentication authentication) {
    User user = ((UserPrinciple) authentication.getPrincipal()).getUser();
    return family.getId().equals(user.getFamily().getId());
  }
}
