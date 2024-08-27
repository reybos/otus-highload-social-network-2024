package rey.bos.highload.dialog.io.repository.custom;

import rey.bos.highload.dialog.io.entity.Participant;

public interface ParticipantRepositoryCustom {

    Participant findByDialogIdAndUserIdOrThrow(long dialogId, String userId);

}
