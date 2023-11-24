package sampm.uz.library_system.model.request;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import sampm.uz.library_system.entity.Book;
import sampm.uz.library_system.entity.Roles;
import sampm.uz.library_system.enums.*;

import java.util.List;

@Data
public class StudentRequest {

    private String fullName;

    private String email;

    private String password;

    private StudentClass studentGrade;

    private Gender gender;

    private SchoolName schoolName;

    private Status status;

    private List<Book> books;

    private Long roleId;

    private boolean graduated;

}
