package rey.bos.highload.dialog.io.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("dialog")
@Data
@Builder
public class Dialog {

    @Id
    private Long id;

    private String dialogId;

    private DialogType type;

}
