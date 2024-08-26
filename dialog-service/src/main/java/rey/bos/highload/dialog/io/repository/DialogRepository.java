package rey.bos.highload.dialog.io.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rey.bos.highload.dialog.io.entity.Dialog;

@Repository
public interface DialogRepository extends CrudRepository<Dialog, Long> {

    boolean existsByDialogId(String dialogId);

}
