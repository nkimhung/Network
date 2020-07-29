package network.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import network.data.models.Relationship;
import network.data.models.User;
import network.interceptor.HasRole;
import network.services.IRelationshipService;
import network.services.IUserService;
import network.util.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class RelationShipController {
    @Autowired 
    private IRelationshipService relationshipService;
    @Autowired
    private IUserService iUserService;


    @GetMapping("/AllRelationship")
    @HasRole({"User"})
    public ResponseEntity<List<Relationship>> getRelationship(@RequestHeader("AuthToken") String token) throws JsonProcessingException {
        return new ResponseEntity<>(relationshipService.getAllFollow(this.getIdUser(token)),HttpStatus.OK);

    }
    public int getIdUser(String token) throws JsonProcessingException {
        String userName= TokenManager.getInstance().getUsername(token);
        Optional<User> user= iUserService.findByUsername(userName);
        return user.get().getId();
    }
    @PostMapping("/Relationship/{idUserTwo}")
    @HasRole("User")
    public ResponseEntity<String> AddUserInfo(@PathVariable("idUserTwo") Integer idUserTwo, @RequestHeader("AuthToken") String token) throws Exception {
        Relationship relationship=new Relationship();
        relationship.setUserOneId(this.getIdUser(token));
        relationship.setUserTwoId(idUserTwo);
        relationship.setActionUserId(1);
        relationshipService.save(relationship);
        return new ResponseEntity<>("Ok!",HttpStatus.OK);

    }
    @PostMapping("/ReadRelationship/{id}")
    @HasRole("User")
    public ResponseEntity<String> AddUserInfo(@PathVariable("id") Integer id) throws Exception {
        relationshipService.read(id);
        return new ResponseEntity<>("Ok!",HttpStatus.OK);

    }
    @DeleteMapping("/Relationship/{id}")
    @HasRole("User")
    public ResponseEntity<String> Delete(@PathVariable("id") Integer id, @RequestHeader("AuthToken") String token) throws JsonProcessingException {
        int idOne =this.getIdUser(token);
        relationshipService.delete(idOne,id);
        return new ResponseEntity<>("delete!",HttpStatus.OK);
    }
}
