package rey.bos.highload.dialog.service;

import rey.bos.highload.dialog.shared.dto.MessageDto;

import java.util.List;

public interface DialogService {

    void sendMessage(String dialogId, MessageDto messageDto);

    List<MessageDto> getDialog(String dialogId);

}
