package com.evanbuss.shopapi.controller;

import com.evanbuss.shopapi.message.request.list.CreateListRequest;
import com.evanbuss.shopapi.models.Family;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/list")
public class ListController {

  ListRepository listRepository;
  FamilyRepository familyRepository;

  @Autowired
  public ListController(ListRepository listRepository, FamilyRepository familyRepository) {
    this.listRepository = listRepository;
    this.familyRepository = familyRepository;
  }

  @PostMapping
  public ResponseEntity<?> createList(
      @RequestBody @Valid CreateListRequest request, Authentication authentication) {
    User user = ((UserPrinciple) authentication.getPrincipal()).getUser();

    if (user.getFamily() == null) {
      return ResponseEntity.badRequest().body("Please create a family first.");
    }
    // Create new list
    List newList = new List(request.getTitle(), request.getDescription(), user.getFamily());

    Optional<Family> famOpt = familyRepository.findById(user.getFamily().getId());
    if (famOpt.isEmpty()) {
      return ResponseEntity.badRequest().body("Please create a family first.");
    }
    Family family = famOpt.get();
    family.getLists().add(newList);

    familyRepository.save(family);

    return ResponseEntity.ok(newList);
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
