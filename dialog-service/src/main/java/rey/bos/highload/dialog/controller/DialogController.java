package rey.bos.highload.dialog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import rey.bos.highload.dialog.api.DialogApi;
import rey.bos.highload.dialog.model.DialogMessage;
import rey.bos.highload.dialog.model.NewMessage;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class DialogController implements DialogApi {

    @Override
    public ResponseEntity<List<DialogMessage>> getDialog(String dialogId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> sendMessage(String dialogId, NewMessage newMessage) {
        return null;
    }

}
