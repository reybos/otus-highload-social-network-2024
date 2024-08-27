package rey.bos.highload.dialog.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rey.bos.highload.dialog.io.entity.Message;
import rey.bos.highload.dialog.io.entity.Participant;
import rey.bos.highload.dialog.io.repository.MessageRepository;
import rey.bos.highload.dialog.io.repository.ParticipantRepository;
import rey.bos.highload.dialog.service.MessageService;
import rey.bos.highload.dialog.shared.dto.MessageDto;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final ParticipantRepository participantRepository;
    private final MessageRepository messageRepository;

    @Override
    public void sendMessage(long dialogId, MessageDto messageDto) {
        Participant participant = participantRepository.findByDialogIdAndUserIdOrThrow(
            dialogId, messageDto.getUserId()
        );
        Message message = Message.builder()
            .message(messageDto.getMessage())
            .dialogId(dialogId)
            .participantId(participant.getId())
            .build();
        messageRepository.save(message);
    }

}
