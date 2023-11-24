package sampm.uz.library_system.model.response;

import lombok.Data;
import sampm.uz.library_system.enums.SchoolName;
import sampm.uz.library_system.enums.Status;
import sampm.uz.library_system.enums.StudentClass;

import java.util.List;

@Data
public class StudentResponse {

    private Long id;

    private String fullName;

    private String email;

    private StudentClass studentGrade;

    private SchoolName schoolName;

    private boolean graduated;

    private Status status;

//    private List<BookResponse> books;
}
