package com.example.model.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentHomeworkRequest {

    private Integer id;

    @Min(value = 1,message = "must be topicNumber")
    private int topicNumber;

    @Min(value = 1,message = "must be lessonHour")
    private int lessonHour;

    @NotBlank(message = "must be homework")
    private String homework;

    private String description;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate date;

    @Min(value = 1,message = "must be subjectLevelId")
    private Integer subjectLevelId;

    @Min(value = 1,message = "must be teacherId")
    private Integer teacherId;

    @Min(value = 1,message = "must be studentClassId")
    private Integer studentClassId;

    @Min(value = 1,message = "must be branchId")
    private Integer branchId;
}
