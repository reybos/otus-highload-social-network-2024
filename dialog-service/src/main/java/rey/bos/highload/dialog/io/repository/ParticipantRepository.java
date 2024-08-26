package rey.bos.highload.dialog.io.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rey.bos.highload.dialog.io.entity.Participant;

@Repository
public interface ParticipantRepository extends CrudRepository<Participant, Long> {
}
