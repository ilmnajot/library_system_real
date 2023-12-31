package sampm.uz.library_system.entity;

import jakarta.persistence.*;
import lombok.*;
import sampm.uz.library_system.enums.Permission;
import sampm.uz.library_system.enums.RoleName;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class Roles extends BaseLongEntity {


    private String name;

    @Enumerated(EnumType.STRING)
    private  RoleName roleName;

    private boolean deleted;

//    @Enumerated(EnumType.STRING)
//    @ElementCollection
//    public List<Permission> permissions = new ArrayList<>();
}
