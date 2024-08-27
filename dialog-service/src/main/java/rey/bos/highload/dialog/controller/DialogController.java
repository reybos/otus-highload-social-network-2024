package rey.bos.highload.dialog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import rey.bos.highload.dialog.api.DialogApi;
import rey.bos.highload.dialog.model.DialogMessage;
import rey.bos.highload.dialog.model.NewMessage;
import rey.bos.highload.dialog.service.DialogService;
import rey.bos.highload.dialog.shared.dto.MessageDto;
import rey.bos.highload.dialog.shared.mapper.MessageMapper;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class DialogController implements DialogApi {

    private final MessageMapper messageMapper;
    private final DialogService dialogService;

    @Override
    public ResponseEntity<List<DialogMessage>> getDialog(String dialogId) {
        List<MessageDto> messages = dialogService.getDialog(dialogId);
        return ResponseEntity.ok(messageMapper.mapDto(messages));
    }

    @Override
    public ResponseEntity<Void> sendMessage(String dialogId, NewMessage newMessage) {
        MessageDto messageDto = messageMapper.map(newMessage);
        dialogService.sendMessage(dialogId, messageDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
