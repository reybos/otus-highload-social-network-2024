package rey.bos.highload.social.network.io.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.HashSet;

@Table(name = "roles")
@Data
public class Role {

    @Id
    private Long id;

    private String name;

    private Collection<RoleAuthority> authorities = new HashSet<>();

    private void addAuthority(Authority authority) {
        authorities.add(createRoleAuthority(authority));
    }

    private RoleAuthority createRoleAuthority(Authority authority) {
        Assert.notNull(authority, "Authority must not be null");
        Assert.notNull(authority.getId(), "Authority id, must not be null");

        return RoleAuthority.builder().authorityId(authority.getId()).build();
    }

}
