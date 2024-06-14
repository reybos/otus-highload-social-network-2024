package rey.bos.highload.social.network.core.io.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("user_role")
@Data
@Builder
public class UserRole {

    @Id
    private Long id;

    private long roleId;

    private long userId;

}
