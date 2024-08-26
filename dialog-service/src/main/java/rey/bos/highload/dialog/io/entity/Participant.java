package rey.bos.highload.dialog.io.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("participant")
@Data
@Builder
public class Participant {

    @Id
    private Long id;

    private long dialogId;

    private String userId;

}
