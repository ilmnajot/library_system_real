package sampm.uz.library_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "student_book")
public class StudentBook extends BaseLongEntity {


    @ManyToOne
    @JoinColumn(insertable = false, updatable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false)
    private Book book;

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "book_id")
    private Long bookId;
}
