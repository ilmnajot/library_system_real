package sampm.uz.library_system.model.response;
import jakarta.persistence.*;
import lombok.Data;
import sampm.uz.library_system.entity.Author;
import sampm.uz.library_system.entity.Book;
import sampm.uz.library_system.enums.Category;

import java.util.List;

@Data
public class MyBooks {

    private int count;

    private List<Book> bookList;

}
