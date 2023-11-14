package sampm.uz.library_system.model.response;
import jakarta.persistence.*;
import lombok.Data;
import sampm.uz.library_system.entity.Author;
import sampm.uz.library_system.entity.Book;
import sampm.uz.library_system.enums.Category;

@Data
public class MyBooks {

    private Long id;

    private String bookName;

    private Long isbn; //13-digit code of the book

    private Long authorId;
}
