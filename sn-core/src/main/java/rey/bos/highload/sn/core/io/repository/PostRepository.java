package rey.bos.highload.sn.core.io.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rey.bos.highload.sn.core.io.entity.Post;

import java.util.Optional;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

    boolean existsByPostId(String postId);

    @Query(
        """
        SELECT *
        FROM post
        WHERE post_id = :postId
            AND user_id = (SELECT id FROM users WHERE user_id = :userId)
            AND NOT deleted
        """
    )
    Optional<Post> findUserPostById(@Param("postId") String postId, @Param("userId") String userId);

}
