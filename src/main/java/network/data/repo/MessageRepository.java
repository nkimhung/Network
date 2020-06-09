package network.data.repo;

import network.data.models.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MessageRepository extends CrudRepository<Message,Integer> {
    @Query(value ="SELECT * FROM  (select * from messages m WHERE m.user_id_from = ?1 order by m.time_created DESC) as m group by m.user_id_to   ", nativeQuery = true)
    List<Message> findAllByUserIdFrom(Integer userIdFrom);
    @Query(value = "SELECT * FROM (select * from messages m WHERE m.user_id_to = ?1 and m.user_id_from not in (select m.user_id_to from messages m where user_id_from = ?1 group by user_id_to) order by time_created DESC ) as m group by m.user_id_from ",nativeQuery = true)
    List<Message> findAllByUserIdTo(Integer userIdTo);

    @Query(value ="select * from messages m WHERE (m.user_id_from = ?1 and m.user_id_to = ?2) or (m.user_id_from = ?2 and m.user_id_to = ?1) order by m.time_created DESC", nativeQuery = true)
    List<Message> findAllWithUserTO(Integer userIdFrom,Integer userIdTo);
}
