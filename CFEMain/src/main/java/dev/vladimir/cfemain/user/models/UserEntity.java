package dev.vladimir.cfemain.user.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private String uuid = String.valueOf(UUID.randomUUID());
    private Set<UserRole> roles = Set.of(UserRole.USER);
    private String createdDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

    public UserEntity(SimpleUser simpleUser) {
        this.username = simpleUser.getUsername();
        this.password = simpleUser.getPassword();
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public Long getId() {
        return id;
    }

    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    public Set<UserRole> getRoles() {
        return roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserEntity userEntity = (UserEntity) o;
        return getId() != null && Objects.equals(getId(), userEntity.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Transient
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Transient
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Transient
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Transient
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Transient
    @Override
    public boolean isEnabled() {
        return true;
    }
}
