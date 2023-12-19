package com.example.entity;

import com.example.enums.Gender;
import com.example.enums.Permissions;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode()
@Builder
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String surname;

    private String fatherName;

    private String phoneNumber;

    private String password;

    private String email;

    private int workDays;

    private int inn;

    private int inps;

    private String biography;

    private boolean married;

    private LocalDate birthDate;

    private LocalDateTime registeredDate;

    private boolean blocked;

    private String fireBaseToken;

    private Integer verificationCode;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne(cascade = CascadeType.ALL)
    private Attachment profilePhoto;

    @ManyToOne(fetch = FetchType.LAZY)
    private Role role;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Branch branch;

    @ManyToMany
    private List<SubjectLevel> subjectLevels;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Permissions> permissions = this.role.getPermissions();
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Permissions permission : permissions) {
            grantedAuthorities.add(new SimpleGrantedAuthority(permission.name()));
        }
        return grantedAuthorities;
    }



    @Override
    public String getUsername() {
        return phoneNumber;
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
        return true;
    }
}
