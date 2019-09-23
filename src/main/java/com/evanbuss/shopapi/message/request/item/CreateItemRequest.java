package com.evanbuss.shopapi.message.request.item;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateItemRequest {
  @NotNull
  private Long listID;
  @NotBlank
  private String title;
  private String description;

  public long getListID() {
    return listID;
  }

  public void setListID(long listID) {
    this.listID = listID;
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
}
