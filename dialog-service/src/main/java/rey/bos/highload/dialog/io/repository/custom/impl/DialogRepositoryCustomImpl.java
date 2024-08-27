package rey.bos.highload.dialog.io.repository.custom.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import rey.bos.highload.dialog.exception.DialogNotFoundException;
import rey.bos.highload.dialog.io.entity.Dialog;
import rey.bos.highload.dialog.io.repository.DialogRepository;
import rey.bos.highload.dialog.io.repository.custom.DialogRepositoryCustom;

import java.util.Optional;

@RequiredArgsConstructor
public class DialogRepositoryCustomImpl implements DialogRepositoryCustom {

    private final ApplicationContext context;
    private DialogRepository dialogRepository;

    private DialogRepository getDialogRepository() {
        if (dialogRepository == null) {
            dialogRepository = context.getBean(DialogRepository.class);
        }
        return dialogRepository;
    }

    @Override
    public Dialog findByDialogIdOrThrow(String dialogId) {
        Optional<Dialog> dialogO = getDialogRepository().findByDialogId(dialogId);
        return dialogO.orElseThrow(
            () -> new DialogNotFoundException(dialogId)
        );
    }

}
