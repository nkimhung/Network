package network.data.repo;
import network.data.models.UserInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserInfoRepository extends CrudRepository<UserInfo,Integer> {
    @Query(value ="select ui.* from user_info ui inner join users u on u.users_info_id =ui.id inner join relationship r on r.user_two_id=?1 and u.id=r.user_one_id", nativeQuery = true)
    List<UserInfo> findAllUserFollow(Integer userId);
    @Query(value ="select ui.* from user_info ui inner join users u on u.users_info_id =ui.id inner join relationship r on r.user_one_id=?1 and u.id=r.user_two_id", nativeQuery = true)
    List<UserInfo> findAllUserByFollow(Integer userId);
}
