package sampm.uz.library_system.model.response;

import jakarta.persistence.*;
import lombok.Data;
import sampm.uz.library_system.entity.Author;
import sampm.uz.library_system.enums.Category;

@Data
public class BookResponse {
    private Long id;

    private String bookName;

    private Long isbn; //13-digit code of the book

    private String description;

    private Category category;

    private boolean available;

    private AuthorResponse author;
}
