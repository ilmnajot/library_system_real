package com.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String aboutAchievement;

    @OneToOne(cascade = CascadeType.ALL)
    private Attachment photoCertificate;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
}
