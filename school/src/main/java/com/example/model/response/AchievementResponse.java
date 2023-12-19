package com.example.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AchievementResponse {

    private Integer id;

    private String name;

    private String userName;

    private String aboutAchievement;

    private Integer photoCertificateId;

    private Integer userId;
}
