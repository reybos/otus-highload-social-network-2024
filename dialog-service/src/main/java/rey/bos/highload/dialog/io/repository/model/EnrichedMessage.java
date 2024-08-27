package rey.bos.highload.dialog.io.repository.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnrichedMessage {

    private String message;

    private String userId;

}
