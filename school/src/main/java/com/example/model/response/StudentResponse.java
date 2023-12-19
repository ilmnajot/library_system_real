package com.example.model.response;

import com.example.entity.Branch;
import com.example.entity.Family;
import lombok.Data;

import java.util.List;

@Data
public class StudentResponse {

    private Integer id;

    private String firstName;

    private String lastName;

    private String fatherName;

    private String phoneNumber;

    private double paymentAmount;

    private String birthDate;

    private String docNumber;

    private List<Integer> docPhotoIds;

    private Integer referenceId;

    private Integer photoId;

    private StudentClassResponse studentClass;

    private boolean active;

    private Branch branch;

    private List<Family> families;

    private String addedTime;

    private Integer medDocPhotoId;
}
