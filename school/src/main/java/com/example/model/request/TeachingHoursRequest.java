package com.example.model.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeachingHoursRequest {

    private Integer id;

    @Min(value = 1)
    private int lessonHours;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @Column(nullable = false)
    private LocalDate date;

    @Min(value = 1)
    private Integer teacherId;

    @Min(value = 1)
    private Integer subjectLevelId;

    @Min(value = 1)
    private Integer typeOfWorkId;

    @Min(value = 1)
    private Integer studentClassId;
}
