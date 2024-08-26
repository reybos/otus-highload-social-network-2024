package rey.bos.highload.dialog.io.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("message")
@Data
@Builder
public class Message {

    @Id
    private Long id;

    private long dialogId;

    @Column("participant_id")
    private Participant participant;

    private String message;

}
