package rey.bos.highload.social.network.io.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

@Table("users")
@Data
public class User {

    @Id
    private Long id;

    private String userId;

    private String firstName;

    private String secondName;

    private LocalDate birthDay;

    private String biography;

    private String city;

    private String encryptedPassword;

    private Collection<UserRole> roles = new HashSet<>();

    private void addRole(Role role) {
        roles.add(createUserRole(role));
    }

    private UserRole createUserRole(Role role) {
        Assert.notNull(role, "Role must not be null");
        Assert.notNull(role.getId(), "Role id, must not be null");

        return UserRole.builder().roleId(role.getId()).build();
    }

}
