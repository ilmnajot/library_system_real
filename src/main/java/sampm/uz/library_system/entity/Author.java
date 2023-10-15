package sampm.uz.library_system.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "author")
public class Author extends BaseLongEntity {

    private String fullName;

    private String email;

    private String city;
    @OneToMany
    private List<Book> bookList;

}
