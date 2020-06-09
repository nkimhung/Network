package network.data.repo;

import network.data.models.Relationship;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RelationshipRepository extends CrudRepository<Relationship,Integer> {
    public List<Relationship> findAllByUserTwoId(Integer userTwoId);
}
