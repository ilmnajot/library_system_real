package sampm.uz.library_system.entity;

import jakarta.persistence.*;
import lombok.*;
import sampm.uz.library_system.enums.Category;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "books")
public class Book extends BaseLongEntity {

    private String bookName;

    private Long isbn; //13-digit code of the book

    private String description;

    @Enumerated(EnumType.STRING)
    private Category category;

//    private boolean available;

    private int count;

//    private boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Author author;

//    @ManyToOne
//    private Student student;
}
