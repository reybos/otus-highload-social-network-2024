package rey.bos.highload.social.network.core.io.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rey.bos.highload.social.network.core.io.entity.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    List<Role> findByIdIn(List<Long> ids);

    Optional<Role> findByName(String name);

}
