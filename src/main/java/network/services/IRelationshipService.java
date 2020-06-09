package network.services;

import network.data.models.Relationship;

import java.util.List;
import java.util.Optional;

public interface IRelationshipService {
    public Optional<Relationship> get(Integer id);

    public List<Relationship> getAllFollow( Integer userTowId);

    public Relationship save(Relationship relationship);

    public void delete(Integer id);
}
