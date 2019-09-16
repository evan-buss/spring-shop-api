package com.evanbuss.shopapi.controller;

import com.evanbuss.shopapi.message.request.list.CreateListRequest;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PostRemove;

@RestController
@RequestMapping("/api/list")
public class ListController {

  @PostMapping
  public ResponseEntity<?> createList(@RequestBody CreateListRequest request) {
    return ResponseEntity.ok().build();
  }

  @GetMapping
  public ResponseEntity<?> getAllLists() {
    return ResponseEntity.ok().build();
  }
}
