package sampm.uz.library_system.model.response;
import lombok.Data;
import sampm.uz.library_system.enums.Category;
@Data
public class BookResponse {

    private Long id;

    private String bookName;

//    @Size(min = 13, max = 13, message = "message must be 13")
    private Long isbn; //13-digit code of the book

    private String description;

    private Category category;

//    private boolean available;

    private AuthorResponse author;

    private int count;

//    private StudentResponse student;
}
