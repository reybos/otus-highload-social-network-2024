package rey.bos.highload.sn.core.io.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("friend")
@Data
@Builder
public class Friend {

    @Id
    private Long id;

    private long userId;

    private long friendId;

    private boolean deleted;

}
