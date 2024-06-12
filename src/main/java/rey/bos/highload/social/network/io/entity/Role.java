package rey.bos.highload.social.network.io.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;

@Table(name = "roles")
@Data
@Builder
public class Role {

    @Id
    private Long id;

    private String name;

    @MappedCollection(idColumn = "role_id")
    private Set<RoleAuthority> authorities;

    public void addAuthority(Authority authority) {
        if (authorities == null) {
            authorities = new HashSet<>();
        }
        authorities.add(createRoleAuthority(authority));
    }

    private RoleAuthority createRoleAuthority(Authority authority) {
        Assert.notNull(authority, "Authority must not be null");
        Assert.notNull(authority.getId(), "Authority id, must not be null");

        return RoleAuthority.builder().authorityId(authority.getId()).build();
    }

}
