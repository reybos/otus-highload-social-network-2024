package rey.bos.highload.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import rey.bos.highload.chat.api.DialogApi;
import rey.bos.highload.chat.model.DialogMessage;
import rey.bos.highload.chat.model.SendMessageRequest;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class DialogController implements DialogApi {

    @Override
    public ResponseEntity<List<DialogMessage>> getChat(String userId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> sendMessage(String userId, SendMessageRequest sendMessageRequest) {
        return null;
    }

}
