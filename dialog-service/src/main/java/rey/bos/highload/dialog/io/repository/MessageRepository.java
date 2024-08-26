package rey.bos.highload.dialog.io.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rey.bos.highload.dialog.io.entity.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
}
