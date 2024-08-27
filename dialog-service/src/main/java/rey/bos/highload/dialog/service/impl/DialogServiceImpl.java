package rey.bos.highload.dialog.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rey.bos.highload.dialog.io.entity.Dialog;
import rey.bos.highload.dialog.io.repository.DialogRepository;
import rey.bos.highload.dialog.io.repository.MessageRepository;
import rey.bos.highload.dialog.io.repository.model.EnrichedMessage;
import rey.bos.highload.dialog.service.DialogService;
import rey.bos.highload.dialog.service.MessageService;
import rey.bos.highload.dialog.shared.dto.MessageDto;
import rey.bos.highload.dialog.shared.mapper.MessageMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DialogServiceImpl implements DialogService {

    private final DialogRepository dialogRepository;
    private final MessageRepository messageRepository;
    private final MessageService messageService;
    private final MessageMapper messageMapper;

    @Override
    public void sendMessage(String dialogId, MessageDto messageDto) {
        Dialog dialog = dialogRepository.findByDialogIdOrThrow(dialogId);
        messageService.sendMessage(dialog.getId(), messageDto);
    }

    @Override
    public List<MessageDto> getDialog(String dialogId) {
        Dialog dialog = dialogRepository.findByDialogIdOrThrow(dialogId);
        List<EnrichedMessage> messages = messageRepository.findDialogMessages(dialog.getId());
        return messageMapper.mapEntity(messages);
    }

}
