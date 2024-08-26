package rey.bos.highload.sn.core.io.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Table("users")
@Data
@Builder
public class User {

    @Id
    private Long id;

    private String userId;

    private String firstName;

    private String secondName;

    private LocalDate birthdate;

    private String biography;

    private String city;

    private String encryptedPassword;

    @MappedCollection(idColumn = "user_id")
    private Set<UserRole> roles;

    public void addRole(Role role) {
        if (roles == null) {
            roles = new HashSet<>();
        }
        roles.add(createUserRole(role));
    }

    private UserRole createUserRole(Role role) {
        Assert.notNull(role, "Role must not be null");
        Assert.notNull(role.getId(), "Role id, must not be null");

        return UserRole.builder().roleId(role.getId()).build();
    }

}
