package rey.bos.highload.sn.core.io.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rey.bos.highload.sn.core.io.entity.Post;
import rey.bos.highload.sn.core.io.repository.model.PostFeed;

import java.util.List;
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

    @Query(
        """
        SELECT p.post_id AS post_id, p.content AS content, u.user_id AS author_user_id
        FROM friend f
                 LEFT JOIN users u ON u.id = f.friend_id
                 LEFT JOIN post p ON f.friend_id = p.user_id
        WHERE f.user_id = (SELECT id FROM users WHERE users.user_id = :userId)
          AND NOT p.deleted
        ORDER BY p.created_at DESC
        OFFSET :offset LIMIT :limit;
        """
    )
    List<PostFeed> findLatestPostsByUserId(
        @Param("userId") String userId, @Param("offset") int offset, @Param("limit") int limit
    );

}
