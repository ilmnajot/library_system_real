package sampm.uz.library_system.model.request;

import lombok.Data;
import sampm.uz.library_system.entity.Book;
import sampm.uz.library_system.entity.Roles;
import sampm.uz.library_system.enums.SchoolName;
import sampm.uz.library_system.enums.Status;
import sampm.uz.library_system.enums.StudentClass;

import java.util.List;

@Data
public class StudentRequest {

    private String fullName;

    private String email;

    private String password;

    private StudentClass studentGrade;

    private SchoolName schoolName;

    private Status status;

    private List<Book> books;

    private Roles role;

    private boolean graduated;

}
