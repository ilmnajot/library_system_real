package sampm.uz.library_system.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "student_book")
public class StudentBook  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false)
    private Book book;

    private Long student_id;

    private Long book_id;

    private int amount;
}
