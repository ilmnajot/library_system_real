package sampm.uz.library_system.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;
import sampm.uz.library_system.enums.RoleName;

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


}