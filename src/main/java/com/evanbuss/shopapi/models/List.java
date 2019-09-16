package com.evanbuss.shopapi.models;

import javax.persistence.*;

@Entity
public class List {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String title;
  private String description;
  @OneToMany private java.util.List<Item> items;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public java.util.List<Item> getItems() {
    return items;
  }

  public void setItems(java.util.List<Item> items) {
    this.items = items;
  }
}
