package sampm.uz.library_system.entity;

import jakarta.persistence.*;
import lombok.*;
import sampm.uz.library_system.enums.Category;

import java.util.Collection;

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

    private int count;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Author author;


}
