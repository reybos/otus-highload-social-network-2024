package rey.bos.highload.sn.core.io.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rey.bos.highload.sn.core.io.entity.Friend;
import rey.bos.highload.sn.core.io.repository.custom.FriendRepositoryCustom;

import java.util.Optional;

@Repository
public interface FriendRepository extends CrudRepository<Friend, Long>, FriendRepositoryCustom {

    @Query(
        """
        SELECT *
        FROM friend
        WHERE user_id = :userId
            AND friend_id = :friendId
            AND NOT deleted
        ORDER BY user_id
        """
    )
    Optional<Friend> findFriend(@Param("userId") long userId, @Param("friendId") long friendId);

    @Query(
        """
        SELECT *
        FROM friend
        WHERE user_id = (SELECT id FROM users WHERE user_id = :userId)
            AND friend_id = (SELECT id FROM users WHERE user_id = :friendUserId)
            AND NOT deleted
        ORDER BY user_id
        """
    )
    Optional<Friend> findFriend(@Param("userId") String userId, @Param("friendUserId") String friendUserId);

}
