package sampm.uz.library_system.model.request;

import lombok.Data;
import sampm.uz.library_system.entity.Roles;
import sampm.uz.library_system.enums.SchoolName;

@Data
public class UserRequest {

    private String fullName;

    private String email;

    private String workPlace;

    private String position;

    private SchoolName schoolName;

    private Roles role;

    private Long roleId;
}
