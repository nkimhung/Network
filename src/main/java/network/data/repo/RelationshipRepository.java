package network.data.repo;

import network.data.models.Relationship;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RelationshipRepository extends CrudRepository<Relationship,Integer> {
    public List<Relationship> findAllByUserTwoId(Integer userTwoId);
    public Optional<Relationship> findById(Integer id);
    public Optional<Relationship> findByUserOneIdAndUserTwoId(Integer idOne,Integer idTwo);
}
