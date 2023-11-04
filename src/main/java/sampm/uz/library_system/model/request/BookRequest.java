package sampm.uz.library_system.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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


    @NotNull(message = "please enter number of books")
    private int count;

}
