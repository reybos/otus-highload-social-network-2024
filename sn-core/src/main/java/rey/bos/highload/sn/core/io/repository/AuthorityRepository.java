package rey.bos.highload.sn.core.io.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rey.bos.highload.sn.core.io.entity.Authority;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorityRepository extends CrudRepository<Authority, Long> {

    List<Authority> findByIdIn(List<Long> ids);

    Optional<Authority> findByName(String name);

}
