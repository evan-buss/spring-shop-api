package com.evanbuss.shopapi.message.response.auth;

public class SuccessfulAuth {

  private String token;

  public SuccessfulAuth(String accessToken) {
    this.token = accessToken;
  }

  public String getAccessToken() {
    return token;
  }

  public void setAccessToken(String accessToken) {
    this.token = accessToken;
  }
}
