package com.example.model.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AchievementDto {

    private Integer id;

    private String name;

    private String aboutAchievement;

    private Integer photoCertificateId;

    private Integer userId;
}
