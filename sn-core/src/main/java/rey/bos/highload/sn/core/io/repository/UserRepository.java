package rey.bos.highload.sn.core.io.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rey.bos.highload.sn.core.io.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUserId(String userId);

    boolean existsByUserId(String userId);

    @Query(
        """
        SELECT *
        FROM users
        WHERE first_name ILIKE CONCAT(:firstName, '%')
            AND second_name ILIKE CONCAT(:secondName, '%')
        ORDER BY user_id
        """
    )
    List<User> findByFirstAndSecondName(@Param("firstName") String firstName, @Param("secondName") String secondName);

}
