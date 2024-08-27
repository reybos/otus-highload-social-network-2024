package rey.bos.highload.dialog.io.repository.custom;

import rey.bos.highload.dialog.io.entity.Dialog;

public interface DialogRepositoryCustom {

    Dialog findByDialogIdOrThrow(String dialogId);

}
