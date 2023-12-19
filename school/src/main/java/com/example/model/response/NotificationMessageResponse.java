package com.example.model.response;

import lombok.*;

import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationMessageResponse {

    private String receiverToken;
    private String title;

    private String body;
    private HashMap<String, String> data;

    public static NotificationMessageResponse from(String token, String massage, HashMap<String ,String> data) {

        return NotificationMessageResponse.builder()
                .receiverToken(token)
                .title(massage)
                .data(data)
                .build();
    }
}
