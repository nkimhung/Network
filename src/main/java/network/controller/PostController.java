package network.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import network.data.models.*;
import network.interceptor.HasRole;
import network.services.*;
import network.util.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PostController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IPostService iPostService;
    @GetMapping("/Post/{id}")
    @HasRole({"User"})
    public ResponseEntity<Post> getPost(@PathVariable("id") Integer id) {
        Post post = iPostService.getByID(id);
        if (post==null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(post,HttpStatus.OK);
        }

    }
    @GetMapping("/Post")
    @HasRole({"User"})
    public ResponseEntity<List<Post>> showPost() {
        List<Post> posts = iPostService.getAll();
        if (posts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(posts, HttpStatus.OK);

    }
    public int getIdUser(String token) throws JsonProcessingException {
        String userName= TokenManager.getInstance().getUsername(token);
        Optional<User> user= iUserService.findByUsername(userName);
        return user.get().getId();
    }
    @GetMapping("/PostFollow/{idFollow}")
    @HasRole("User")
    public ResponseEntity<List<Post>> GetAllPostFollow( @RequestHeader("AuthToken") String token,@PathVariable("idFollow") Integer userIdFollow) throws Exception {
        List<Post> list= iPostService.getAllFollowByUser( this.getIdUser(token),userIdFollow);
        return new ResponseEntity<>(list,HttpStatus.OK);

    }
    @GetMapping("/MyPost")
    @HasRole("User")
    public ResponseEntity<List<Post>> GetAllMyPost( @RequestHeader("AuthToken") String token) throws Exception {
        List<Post> list= iPostService.getAllMyPost(this.getIdUser(token));
        return new ResponseEntity<>(list,HttpStatus.OK);

    }
    @GetMapping("/MyPost/{idInfo}")
    @HasRole("User")
    public ResponseEntity<List<Post>> GetAllMyPost(@PathVariable("idInfo") Integer userIdInfo) throws Exception {
        List<Post> list= iPostService.getAllMyPost(iUserService.findByUserInfo(userIdInfo).get().getId());
        return new ResponseEntity<>(list,HttpStatus.OK);

    }
    @GetMapping("/MyImage")
    @HasRole("User")
    public ResponseEntity<List<String>> GetAllMyImage( @RequestHeader("AuthToken") String token) throws Exception {
        List<String> list= iPostService.getAllMyImage(this.getIdUser(token));
        return new ResponseEntity<>(list,HttpStatus.OK);

    }
    @GetMapping("/MyImage/{idInfo}")
    @HasRole("User")
    public ResponseEntity<List<String>> GetAllImageUser(@PathVariable("idInfo") Integer userIdInfo) throws Exception {
        List<String> list= iPostService.getAllMyImage(iUserService.findByUserInfo(userIdInfo).get().getId());
        return new ResponseEntity<>(list,HttpStatus.OK);

    }
    @GetMapping("/PostFollow")
    @HasRole("User")
    public ResponseEntity<List<Post>> GetAllPostFollow( @RequestHeader("AuthToken") String token) throws Exception {
        List<Post> list= iPostService.getAllFollow( this.getIdUser(token));
        return new ResponseEntity<>(list,HttpStatus.OK);

    }
    @PostMapping("/Post")
    @HasRole("User")
    public ResponseEntity<String> AddPost( @RequestHeader("AuthToken") String token,@RequestBody Post post) throws Exception {
        if(post!=null){
            post.setUser_id(this.getIdUser(token));
            iPostService.save(post);
            return new ResponseEntity<>("Create!", HttpStatus.CREATED);
        }else
            return new ResponseEntity<>("bai viet bi null",HttpStatus.NOT_FOUND);

    }
    @PutMapping("/Post")
    @HasRole("User")
    public ResponseEntity<String> Update( @RequestHeader("AuthToken") String token,@RequestBody Post post) throws Exception {
        if(post.getUser_id()==this.getIdUser(token)){
            iPostService.update(post);
            return new ResponseEntity<>("Update!", HttpStatus.OK);
        }else
            return new ResponseEntity<>("khong co quyen sua bai viet cua nguoi khac",HttpStatus.UNAUTHORIZED);

    }
    @DeleteMapping("/Post/{id}")
    @HasRole("User")
    public ResponseEntity<String> Delete(@PathVariable("id") Integer id, @RequestHeader("AuthToken") String token) throws JsonProcessingException {
        Post currentPost = iPostService.getByID(id);
        if(currentPost.getUser_id()!=this.getIdUser(token)){
            return new ResponseEntity<>("Khong co quyen xoa bai viet cua nguoi khac",HttpStatus.UNAUTHORIZED);
        }
        iPostService.delete(id);
        return new ResponseEntity<>("Delete!",HttpStatus.OK);
    }

}
