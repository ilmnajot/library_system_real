package sampm.uz.library_system.model.request;
import jakarta.persistence.Column;
import lombok.Data;
import sampm.uz.library_system.entity.Book;
import sampm.uz.library_system.entity.Student;
@Data
public class StudentBookRequest {

    private Long id;

    private Student student;

    private Book book;

    @Column(name = "student_id")
    private Long student_id;

    @Column(name = "book_id")
    private Long book_id;

    private int amount;
}
