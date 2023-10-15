package sampm.uz.library_system.model.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.Data;
import sampm.uz.library_system.entity.Book;
import sampm.uz.library_system.enums.SchoolName;
import sampm.uz.library_system.enums.Status;
import sampm.uz.library_system.enums.StudentClass;

import java.util.ArrayList;
import java.util.List;

@Data
public class StudentResponse {

    private String fullName;

    private String email;

    private StudentClass studentGrade;

    private SchoolName schoolName;

    private boolean available;

    private Status status;

    private List<BookResponse> books;
}
