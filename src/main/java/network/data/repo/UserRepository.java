package network.data.repo;

import network.data.models.Post;
import network.data.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Integer> {
    public Optional<User> findByUsername(String username);
    @Query(value ="select u.* from users u where u.users_info_id =?1 limit 1", nativeQuery = true)
    public Optional<User> findId(Integer userInfoId);
}
