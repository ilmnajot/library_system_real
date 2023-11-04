package sampm.uz.library_system.model.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import lombok.Data;
import sampm.uz.library_system.entity.Roles;
import sampm.uz.library_system.enums.SchoolName;
import sampm.uz.library_system.enums.Status;
import sampm.uz.library_system.enums.StudentClass;

@Data
public class StudentStatusResponse {
    private String fullName;

    private String email;

//    private String password;

//    @Enumerated(EnumType.STRING)
    private StudentClass studentGrade;

//    @Enumerated(EnumType.STRING)
    private SchoolName schoolName;

//    private String gmailCode;

    private boolean graduated = false;

    @Enumerated(EnumType.STRING)
    private Status status;

//    @OneToOne
//    @Enumerated(EnumType.STRING)
//    private Roles role;

    private int numberOfBooks;
}
