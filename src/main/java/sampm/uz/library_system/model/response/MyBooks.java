package sampm.uz.library_system.model.response;
import lombok.Data;
import sampm.uz.library_system.entity.Book;
@Data
public class MyBooks {

    private Long id;

    private Book book;

    private Long book_id;

    private int amount;
}
