package com.evanbuss.shopapi.controller;

import com.evanbuss.shopapi.ResponseError;
import com.evanbuss.shopapi.message.request.list.CreateListRequest;
import com.evanbuss.shopapi.models.List;
import com.evanbuss.shopapi.models.User;
import com.evanbuss.shopapi.repository.FamilyRepository;
import com.evanbuss.shopapi.repository.ListRepository;
import com.evanbuss.shopapi.security.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/list")
public class ListController {

  private ListRepository listRepository;

  @Autowired
  public ListController(ListRepository listRepository, FamilyRepository familyRepository) {
    this.listRepository = listRepository;
  }

  @PostMapping
  public ResponseEntity<?> createList(
      @RequestBody @Valid CreateListRequest request, Authentication authentication) {
    User user = ((UserPrinciple) authentication.getPrincipal()).getUser();

    // User doesn't have a family...
    if (user.getFamily() == null) {
      return ResponseEntity.badRequest().body(ResponseError.NO_FAMILY);
    }
    // Create new list
    List newList = new List(request.getTitle(), request.getDescription(), user.getFamily());
    List savedList = listRepository.save(newList);
    return ResponseEntity.ok(savedList);
  }

  @GetMapping
  public ResponseEntity<?> getAllLists(Authentication authentication) {
    User user = ((UserPrinciple) authentication.getPrincipal()).getUser();

    if (user.getFamily() == null) {
      return ResponseEntity.badRequest().build();
    }

    java.util.List<List> lists = listRepository.getListsByFamilyId(user.getFamily().getId());

    return ResponseEntity.ok(lists);
  }
}
