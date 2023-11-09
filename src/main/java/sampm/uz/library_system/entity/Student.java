package sampm.uz.library_system.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sampm.uz.library_system.enums.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "students")
public class Student extends BaseLongEntity implements UserDetails {

    private String fullName;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private StudentClass studentGrade;

    @Enumerated(EnumType.STRING)
    private SchoolName schoolName;

    private String gmailCode;

    private boolean graduated = false;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne
    @Enumerated(EnumType.STRING)
    private Roles role;

    private int numberOfBooks;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "role_id", insertable = false, updatable = false)
    private Long roleId;

    private boolean enabled;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "student_book",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(this.getRole().getName());
        return Collections.singleton(simpleGrantedAuthority);
    }
    @Override
    public String getPassword(){
        return this.getPassword();
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
