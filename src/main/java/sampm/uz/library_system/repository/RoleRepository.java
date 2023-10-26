package sampm.uz.library_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sampm.uz.library_system.entity.Roles;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {

}
