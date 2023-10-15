package sampm.uz.library_system.model.request;

import lombok.Data;

@Data
public class AuthorRequest {
    private String fullName;

    private String email;

    private String city;
}
