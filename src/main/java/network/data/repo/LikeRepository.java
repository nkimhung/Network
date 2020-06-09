package network.data.repo;

import network.data.models.Like;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LikeRepository extends CrudRepository<Like,Integer> {
    public List<Like> findByPost(Integer post_id);

    public List<Like> findAllByUserAndPost(Integer user, Integer post);
}
