package com.example.kitchen.model.request;

import com.example.enums.WeekDays;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DailyMealRequest {

    private Integer id;

    private String name;

    private Integer photoId;

    private WeekDays day;

    private String time;

    private Integer branchId;
}
