package rey.bos.highload.dialog.shared.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageDto {

    private String userId;
    private String message;

}
