package rey.bos.highload.dialog.io.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rey.bos.highload.dialog.io.entity.Dialog;
import rey.bos.highload.dialog.io.repository.custom.DialogRepositoryCustom;

import java.util.Optional;

@Repository
public interface DialogRepository extends CrudRepository<Dialog, Long>, DialogRepositoryCustom {

    boolean existsByDialogId(String dialogId);

    Optional<Dialog> findByDialogId(String dialogId);

}
