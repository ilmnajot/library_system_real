package sampm.uz.library_system.entity;

import jakarta.persistence.*;
import lombok.*;
import sampm.uz.library_system.enums.SchoolName;
import sampm.uz.library_system.enums.Status;
import sampm.uz.library_system.enums.StudentClass;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "students")
public class Student extends BaseLongEntity {

    private String fullName;

    private String email;

    @Enumerated(EnumType.STRING)
    private StudentClass studentGrade;

    @Enumerated(EnumType.STRING)
    private SchoolName schoolName;

    private String gmailCode;

    private boolean available;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToMany
    private List<Book> books;

    @OneToOne
    @Enumerated(EnumType.STRING)
    private Role role;


}
