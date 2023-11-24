package sampm.uz.library_system.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import sampm.uz.library_system.entity.Author;
import sampm.uz.library_system.entity.Book;
import sampm.uz.library_system.enums.Category;

@Data
@NoArgsConstructor
public class BookResponseByAuthorId {

    private Long id;

    private String bookName;

    private Long isbn;

    private Category category;





}
