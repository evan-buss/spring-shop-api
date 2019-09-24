package com.evanbuss.shopapi.message.request.item;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateItemRequest {
  @NotNull
  private Long listID;
  @NotBlank
  private String title;
  private String description;
  private byte[] image;

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

  public byte[] getImage() {
    return image;
  }

  public void setImage(byte[] image) {
    this.image = image;
  }
}
