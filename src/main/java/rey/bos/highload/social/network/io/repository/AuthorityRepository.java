package rey.bos.highload.social.network.io.repository;

import org.springframework.data.repository.CrudRepository;
import rey.bos.highload.social.network.io.entity.Authority;

import java.util.List;

public interface AuthorityRepository extends CrudRepository<Authority, Long> {

    List<Authority> findByIdIn(List<Long> ids);

}
