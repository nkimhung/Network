package network.services;

import network.data.models.Relationship;
import network.exception.LogicException;

import java.util.List;

public interface IRelationshipService {
    public Relationship get(Integer id);

    public List<Relationship> getAllFollow( Integer userTowId);

    public Relationship save(Relationship relationship) throws LogicException;
    public Relationship read(Integer id);
    public void delete(Integer idOne,Integer idTwo);
}
