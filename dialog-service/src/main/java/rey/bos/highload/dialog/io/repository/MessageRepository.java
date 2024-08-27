package rey.bos.highload.dialog.io.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rey.bos.highload.dialog.io.entity.Message;
import rey.bos.highload.dialog.io.repository.model.EnrichedMessage;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {

    @Query(
        """
        SELECT m.message AS message, p.user_id AS user_id
        FROM message m
                LEFT JOIN participant p ON m.dialog_id = p.dialog_id AND m.participant_id = p.id
        WHERE m.dialog_id = :dialogId;
        """
    )
    List<EnrichedMessage> findDialogMessages(@Param("dialogId") long dialogId);

}
