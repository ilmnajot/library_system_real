package sampm.uz.library_system.model.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sampm.uz.library_system.entity.Book;
import sampm.uz.library_system.enums.SchoolName;
import sampm.uz.library_system.enums.Status;
import sampm.uz.library_system.enums.StudentClass;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class StudentRequest {

    private String fullName;

    private String email;

    private StudentClass studentGrade;

    private SchoolName schoolName;

    private Status status;

    private List<Book> books;


}
