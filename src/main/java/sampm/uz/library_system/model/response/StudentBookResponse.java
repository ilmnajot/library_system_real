package sampm.uz.library_system.model.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sampm.uz.library_system.entity.Book;

@Data
public class StudentBookResponse {


    @Column(name = "student_id")
    private Long student_id;

    @Column(name = "book_id")
    private Long book_id;

    @Column(name = "amount")
    private int amount;

}
