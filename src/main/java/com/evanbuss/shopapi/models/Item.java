package com.evanbuss.shopapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Item {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "list_id", nullable = false)
  @JsonIgnore
  private List list;

  @NotBlank
  private String name;
  private String description;

  public Item() {
  }

  public Item(List list, String name, String description) {
    this.list = list;
    this.name = name;
    this.description = description;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List getList() {
    return list;
  }

  public void setList(List list) {
    this.list = list;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
