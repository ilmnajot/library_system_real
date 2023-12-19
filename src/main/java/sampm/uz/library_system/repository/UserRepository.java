package sampm.uz.library_system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sampm.uz.library_system.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);
//    Optional<User> findUserByIdAndAvailableTrue(Long id);
    Optional<User> findByFullName(String name);

    Page<User> findAll(Sort sort, PageRequest pageRequest);


}
