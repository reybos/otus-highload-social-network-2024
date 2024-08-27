package rey.bos.highload.dialog.factory;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import rey.bos.highload.dialog.io.entity.Dialog;
import rey.bos.highload.dialog.io.entity.DialogType;
import rey.bos.highload.dialog.io.entity.Participant;
import rey.bos.highload.dialog.io.repository.DialogRepository;
import rey.bos.highload.dialog.io.repository.ParticipantRepository;
import rey.bos.highload.dialog.util.Utils;

import java.util.List;

@Component
@Profile("test")
@RequiredArgsConstructor
public class DialogFactory {

    private final Utils utils;
    private final DialogRepository dialogRepository;
    private final ParticipantRepository participantRepository;

    public Dialog createDialog(DialogParams dialogParams) {
        if (dialogParams.dialogId == null) {
            dialogParams.dialogId = utils.generateDialogId(20);
        }
        Dialog dialog = Dialog.builder()
            .dialogId(dialogParams.dialogId)
            .type(dialogParams.type)
            .build();
        Dialog storedDialog = dialogRepository.save(dialog);
        for (String userId : dialogParams.participants) {
            Participant participant = Participant.builder().dialogId(storedDialog.getId()).userId(userId).build();
            participantRepository.save(participant);
        }
        return storedDialog;
    }

    @Builder
    @Getter
    public static class DialogParams {

        @Builder.Default
        private DialogType type = DialogType.CHAT;

        private String dialogId;

        private List<String> participants;

    }

}
