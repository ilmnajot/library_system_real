package sampm.uz.library_system.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sampm.uz.library_system.enums.SchoolName;

import java.util.Collection;

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

    private String password;

    private String workPlace;

    private String position;

    @Enumerated(EnumType.STRING)
    private SchoolName schoolName;

    @ManyToOne
    private Roles role;

    @Column(name = "role_id", insertable = false, updatable = false)
    private Long roleId;


}
