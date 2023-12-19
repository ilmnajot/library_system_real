package com.example.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenResponse {

    private String accessToken;

    private UserResponse userResponse;
     public TokenResponse(String accessToken) {
          this.accessToken=accessToken;
     }
}
