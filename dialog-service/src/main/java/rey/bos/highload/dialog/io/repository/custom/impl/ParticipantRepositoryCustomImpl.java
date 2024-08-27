package rey.bos.highload.dialog.io.repository.custom.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import rey.bos.highload.dialog.exception.ParticipantNotFoundException;
import rey.bos.highload.dialog.io.entity.Participant;
import rey.bos.highload.dialog.io.repository.ParticipantRepository;
import rey.bos.highload.dialog.io.repository.custom.ParticipantRepositoryCustom;

import java.util.Optional;

@RequiredArgsConstructor
public class ParticipantRepositoryCustomImpl implements ParticipantRepositoryCustom {


    private final ApplicationContext context;
    private ParticipantRepository participantRepository;

    private ParticipantRepository getParticipantRepository() {
        if (participantRepository == null) {
            participantRepository = context.getBean(ParticipantRepository.class);
        }
        return participantRepository;
    }

    @Override
    public Participant findByDialogIdAndUserIdOrThrow(long dialogId, String userId) {
        Optional<Participant> participantO = getParticipantRepository().findByDialogIdAndUserId(dialogId, userId);
        return participantO.orElseThrow(
            () -> new ParticipantNotFoundException(dialogId, userId)
        );
    }


}
