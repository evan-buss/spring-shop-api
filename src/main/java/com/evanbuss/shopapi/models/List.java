package com.evanbuss.shopapi.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class List {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotBlank
  private String title;
  private String description;

  @ManyToOne
  @JoinColumn
  Family family;

  @OneToMany(mappedBy = "list", cascade = CascadeType.ALL)
  private java.util.List<Item> items;

  public List() {
  }

  public List(String title, String description, Family family) {
    this.title = title;
    this.description = description;
    this.family = family;
  }

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
