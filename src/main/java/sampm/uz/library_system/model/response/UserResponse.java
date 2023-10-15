package sampm.uz.library_system.model.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import sampm.uz.library_system.enums.SchoolName;

@Data
public class UserResponse {

    private String fullName;

    private String email;

    private String workPlace;

    private String position;

    private SchoolName schoolName;
}
