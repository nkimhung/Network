package network.services.implement;

import network.data.models.Relationship;
import network.data.models.UserInfo;
import network.data.repo.RelationshipRepository;
import network.data.repo.UserInfoRepository;
import network.data.repo.UserRepository;
import network.exception.LogicException;
import network.services.IRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RelationshipImpl implements IRelationshipService {
    @Autowired
    RelationshipRepository relationshipRepository;
    @Autowired
    RelationshipImpl relationshipImpl;
    @Autowired
    UserInfoRepository userInfoRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public Relationship get(Integer id) {
        return relationshipImpl.getRelationshipFullInfo(relationshipRepository.findById(id).get());
    }

    @Override
    public List<Relationship> getAllFollow(Integer id) {
        List<Relationship> list= relationshipRepository.findAllByUserTwoId(id);
        int index=0;
        for (Relationship relationship: list
        ) {
            Relationship relationship1 =relationshipImpl.getRelationshipFullInfo(relationship);
            list.set(index,relationship1);
            index++;

        }
        return list;
    }
    public Relationship getRelationshipFullInfo(Relationship relationship){
        UserInfo userInfo =userInfoRepository.findById(userRepository.findById(relationship.getUserOneId()).get().getUsers_info_id()).get();
        relationship.setUserInfo(userInfo);
        return relationship;
    }
    @Override
    public Relationship save(Relationship relationship)throws LogicException {
        int id = userRepository.findById(relationship.getUserTwoId()).get().getId();
        relationship.setUserTwoId(id);
        if(relationshipRepository.findByUserOneIdAndUserTwoId(relationship.getUserOneId(),relationship.getUserTwoId()).isEmpty()) {
            return relationshipRepository.save(relationship);
        }

        throw new LogicException("Da follow roi khong the follow tiep", HttpStatus.BAD_REQUEST);

    }

    @Override
    public Relationship read(Integer id) {
        Relationship relationship = relationshipRepository.findById(id).get();
        relationship.setRead(true);
        return relationshipRepository.save(relationship);
    }

    @Override
    public void delete(Integer idOne,Integer idTwo) {
        int id = userRepository.findById(idTwo).get().getId();
        relationshipRepository.deleteById(relationshipRepository.findByUserOneIdAndUserTwoId(idOne,id).get().getId());
    }
}
