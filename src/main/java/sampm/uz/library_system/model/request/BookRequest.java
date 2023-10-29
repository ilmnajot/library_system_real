package sampm.uz.library_system.model.request;

import lombok.Data;
import sampm.uz.library_system.enums.Category;

@Data
public class BookRequest {

    private String bookName;

    private Long isbn; //13-digit code of the book

    private String description;

    private Category category;

//    private boolean available;

    private Long authorId;

//    private Student student;

    private int count;

}
