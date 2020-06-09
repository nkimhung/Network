package network.services.implement;

import network.data.models.Relationship;
import network.data.repo.RelationshipRepository;
import network.services.IRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RelationshipImpl implements IRelationshipService {
    @Autowired
    RelationshipRepository relationshipRepository;
    @Override
    public Optional<Relationship> get(Integer id) {
        return relationshipRepository.findById(id);
    }

    @Override
    public List<Relationship> getAllFollow(Integer id) {
        return relationshipRepository.findAllByUserTwoId(id);
    }

    @Override
    public Relationship save(Relationship relationship) {
        return relationshipRepository.save(relationship);
    }

    @Override
    public void delete(Integer id) {
        relationshipRepository.deleteById(id);
    }
}
