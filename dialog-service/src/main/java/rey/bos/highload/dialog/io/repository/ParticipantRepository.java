package rey.bos.highload.dialog.io.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rey.bos.highload.dialog.io.entity.Participant;
import rey.bos.highload.dialog.io.repository.custom.ParticipantRepositoryCustom;

import java.util.Optional;

@Repository
public interface ParticipantRepository extends CrudRepository<Participant, Long>, ParticipantRepositoryCustom {

    Optional<Participant> findByDialogIdAndUserId(long dialogId, String UserId);

}
