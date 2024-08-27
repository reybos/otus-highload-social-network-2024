package rey.bos.highload.dialog.shared.mapper;

import org.mapstruct.Mapper;
import rey.bos.highload.dialog.io.repository.model.EnrichedMessage;
import rey.bos.highload.dialog.model.DialogMessage;
import rey.bos.highload.dialog.model.NewMessage;
import rey.bos.highload.dialog.shared.dto.MessageDto;

import java.util.List;

@Mapper(componentModel="spring")
public abstract class MessageMapper {

    public MessageDto map(NewMessage message) {
        return MessageDto.builder()
            .userId(message.getSender())
            .message(message.getText())
            .build();
    }

    public DialogMessage mapDto(MessageDto messageDto) {
        return new DialogMessage(messageDto.getUserId(), messageDto.getMessage());
    }

    public abstract List<DialogMessage> mapDto(List<MessageDto> messages);

    public abstract MessageDto mapEntity(EnrichedMessage message);

    public abstract List<MessageDto> mapEntity(List<EnrichedMessage> messages);

}
