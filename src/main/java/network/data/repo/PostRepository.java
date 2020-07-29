package network.data.repo;

import network.data.models.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post,Integer> {
    @Query(value ="select p.* from posts p inner join relationship r on p.user_id =r.user_two_id and r.user_one_id = ?1 order by p.time_created DESC ", nativeQuery = true)
    List<Post> findAllPostFollow(Integer userId);
    @Query(value ="select p.* from posts p inner join relationship r on p.user_id =r.user_two_id and r.user_one_id = ?1 and r.user_two_id=?2 order by p.time_created DESC ", nativeQuery = true)
    List<Post> findAllPostFollowByUser(Integer userId,Integer userTwo);
    @Query(value ="select p.* from posts p where p.user_id =?1 order by p.time_created DESC ", nativeQuery = true)
    List<Post> findAllMyPost(Integer userId);
}
