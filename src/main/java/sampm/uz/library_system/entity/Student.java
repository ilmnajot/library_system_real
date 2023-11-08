package sampm.uz.library_system.entity;

import jakarta.persistence.*;
import lombok.*;
import sampm.uz.library_system.enums.Gender;
import sampm.uz.library_system.enums.SchoolName;
import sampm.uz.library_system.enums.Status;
import sampm.uz.library_system.enums.StudentClass;

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

    private String password;

    @Enumerated(EnumType.STRING)
    private StudentClass studentGrade;

    @Enumerated(EnumType.STRING)
    private SchoolName schoolName;

    private String gmailCode;

    private boolean graduated = false;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @Enumerated(EnumType.STRING)
    private Roles role;

    private int numberOfBooks;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "role_id", insertable = false, updatable = false)
    private Long roleId;

    private boolean enabled;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "student_book",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books;


}
