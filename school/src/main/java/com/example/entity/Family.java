package com.example.entity;

import com.example.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Family {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    @Size(min = 9,max = 9)
    private String phoneNumber;

    @Column(nullable = false)
    @Size(min = 6)
    private String password;

    private LocalDateTime registeredDate;

    private String fireBaseToken;

    private Gender gender;

    private boolean active;

    @OneToMany
    private List<Student> students;

    @ManyToOne
    private Branch branch;
}
