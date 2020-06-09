package network.data.repo;

import network.data.models.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;

import java.util.Collection;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
    public List<Comment> findByPost(Integer post_id);

}
