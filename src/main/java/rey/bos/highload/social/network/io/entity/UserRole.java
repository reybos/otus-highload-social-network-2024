package rey.bos.highload.social.network.io.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("users_roles")
@Data
@Builder
public class UserRole {

    @Id
    private Long id;

    private long roleId;

}
