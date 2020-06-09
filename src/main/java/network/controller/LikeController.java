package network.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import network.data.models.Like;
import network.data.models.User;
import network.data.models.UserInfo;
import network.interceptor.HasRole;
import network.services.ILikeService;
import network.services.IPostService;
import network.services.IUserInfoService;
import network.services.IUserService;
import network.util.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class LikeController {
    @Autowired 
    private ILikeService iLikeService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IUserInfoService iUserInfoService;
    @Autowired
    private IPostService iPostService;
    @GetMapping("/Like/{id}")
    @HasRole({"User"})
    public ResponseEntity<Like> getLike(@PathVariable("id") Integer id) {
        Like like = iLikeService.getByID(id);
        if (like!=null) {
            return new ResponseEntity<>(like, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("/LikeByPost/{id_post}")
    @HasRole({"User"})
    public ResponseEntity<List<Like>> showLike(@PathVariable("id_post") Integer id_post) {
        List<Like> likeList = iLikeService.getAllLikeByIdPost(id_post);
        if (likeList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(likeList, HttpStatus.OK);

    }
    public int getIdUser(String token) throws JsonProcessingException {
        String userName= TokenManager.getInstance().getUsername(token);
        Optional<User> user= iUserService.findByUsername(userName);
        return user.get().getId();
    }

    @PostMapping("/Like/{idPost}")
    @HasRole("User")
    public ResponseEntity<String> AddUserInfo(@PathVariable("idPost") Integer idPost, @RequestHeader("AuthToken") String token) throws Exception {
        if(iPostService.getByID(idPost)!=null){
            Like like = new Like();
            like.setUser(this.getIdUser(token));
            like.setPost(idPost);
            iLikeService.save(like);
            return new ResponseEntity<>("Create!", HttpStatus.CREATED);
        }else
            return new ResponseEntity<>("id bai viet khong hop le",HttpStatus.NOT_FOUND);

    }
    @DeleteMapping("/like/{id}")
    @HasRole("User")
    public ResponseEntity<String> Delete(@PathVariable("id") Integer id, @RequestHeader("AuthToken") String token) throws JsonProcessingException {
        Like currentLike = iLikeService.getByID(id);
        if(currentLike.getUser()!=this.getIdUser(token)){
            return new ResponseEntity<>("Khong co quyen xoa like cua nguoi khac",HttpStatus.UNAUTHORIZED);
        }
        iLikeService.delete(id);
        return new ResponseEntity<>("Delete!",HttpStatus.OK);
    }
}
