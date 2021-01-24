package web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank(message = "the field can't be empty!")
    private String firstName;

    @NotBlank(message = "the field can't be empty!")
    private String lastName;

    @NotBlank(message = "the field can't be empty!")
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank(message = "the field can't be empty!")
    @Column(name = "password")
    private String password;

    @Column(name = "age")
    private byte age;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Role> roles;

    public User(String firstName, String lastName, byte age, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public String getRolesString() {
        return roles.stream().map(Role::getRole).collect(Collectors.joining(", "));
    }

    public boolean isAdmin() {
        return getRolesString().contains("ROLE_ADMIN");
    }

    public boolean isUser() {
        return getRolesString().contains("ROLE_USER");
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
