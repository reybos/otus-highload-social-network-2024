package rey.bos.highload.social.network.io.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("role_authority")
@Data
@Builder
public class RoleAuthority {

    @Id
    private Long id;

    private long authorityId;

    private long roleId;

}
