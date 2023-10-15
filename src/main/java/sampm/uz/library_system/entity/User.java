package sampm.uz.library_system.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;
import sampm.uz.library_system.enums.SchoolName;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User extends BaseLongEntity {

    private String fullName;

    private String email;

    private String workPlace;

    private String position;

    @Enumerated(EnumType.STRING)
    private SchoolName schoolName;

    private String gmailCode;

    private boolean available;

}
