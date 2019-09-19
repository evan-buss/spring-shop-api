package com.evanbuss.shopapi.models;

import javax.persistence.*;

@Entity
public class Item {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "list_id", nullable = false)
  private List list;

  private String name;
  private String description;
}
