package com.example.model.request;

import com.example.enums.Gender;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {

    private Integer id;

    @NotBlank(message = "must be name")
    private String name;

    @NotBlank(message = "must be surname")
    private String surname;

    @NotBlank(message = "must be fatherName")
    private String fatherName;

    @Size(min = 9, max = 9,message = "must be phoneNumber 9 digits")
    private String phoneNumber;

    @Size(min = 6,message = "must be password min 6 digits")
    private String password;

    @Email(message = "the email is not correct")
    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private int inn;

    @Min(value = 1,message = "must be workDays")
    private int workDays;

    private int inps;

    private String biography;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birthDate;

    private Integer profilePhotoId;

    private Integer roleId;

    private List<Integer> subjectLevelIdList;

    private boolean married;

    @Min(value = 1,message = "must be branchId")
    private Integer branchId;
}
