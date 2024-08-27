package rey.bos.highload.dialog.service;

import rey.bos.highload.dialog.shared.dto.MessageDto;

public interface MessageService {

    void sendMessage(long dialogId, MessageDto messageDto);

}
