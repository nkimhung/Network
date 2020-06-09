package network.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import network.data.models.Comment;
import network.data.models.User;
import network.interceptor.HasRole;
import network.services.ICommentService;
import network.services.IPostService;
import network.services.IUserService;
import network.util.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CommentController {
    @Autowired
    private ICommentService iCommentService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IPostService iPostService;

    @GetMapping("/Comment/{id}")
    @HasRole({"User"})
    public ResponseEntity<Comment> getComment(@PathVariable("id") Integer id) {
        Comment comment = iCommentService.getByID(id);
        if(comment==null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(comment, HttpStatus.OK);

    }
    @GetMapping("/CommentByPost/{id_post}")
    @HasRole({"User"})
    public ResponseEntity<List<Comment>> showComment(@PathVariable("id_post") Integer id_post) {
        List<Comment> commentList = iCommentService.getAllByIdPost(id_post);
        if (commentList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(commentList, HttpStatus.OK);

    }
    public int getIdUser(String token) throws JsonProcessingException {
        String userName= TokenManager.getInstance().getUsername(token);
        Optional<User> user= iUserService.findByUsername(userName);
        return user.get().getId();
    }
    @PutMapping("/Comment")
    @HasRole("User")
    public ResponseEntity<String> updateUserInfo(@RequestBody Comment comment, @RequestHeader("AuthToken") String token) throws Exception {

        // Kiểm tra có tồn tại comment trong database
        Comment currentComment = iCommentService.getByID(comment.getId());
        if (currentComment == null) {
            return new ResponseEntity<>("Không tồn tại comment",HttpStatus.NOT_FOUND);
        }
        // Kiểm tra co phai comment cua nguoi gui request
        if(this.getIdUser(token)!= comment.getUser()){
            return new ResponseEntity<>("Không có quyền sửa bình luận của người khác",HttpStatus.UNAUTHORIZED);
        }

        Comment comment1 =iCommentService.update(comment);
        return new ResponseEntity<>("Updated!", HttpStatus.OK);


    }

    @PostMapping("/Comment")
    @HasRole("User")
    public ResponseEntity<String> AddUserInfo(@RequestBody Comment comment, @RequestHeader("AuthToken") String token) throws Exception {
        if(iPostService.getByID(comment.getPost())!=null){
            comment.setUser(this.getIdUser(token));
            iCommentService.save(comment);
            return new ResponseEntity<>("Create!", HttpStatus.CREATED);

        }
        else return new ResponseEntity<>("id bai viet khong hop le",HttpStatus.NOT_FOUND);

    }
    @DeleteMapping("/Comment/{id}")
    @HasRole("User")
    public ResponseEntity<String> Delete(@PathVariable("id") Integer id, @RequestHeader("AuthToken") String token) throws JsonProcessingException {
        Comment currentComment = iCommentService.getByID(id);
        if(currentComment.getUser()!=this.getIdUser(token)){
            return new ResponseEntity<>("Khong co quyen xoa comment cua nguoi khac",HttpStatus.UNAUTHORIZED);
        }
        iCommentService.delete(id);
        return new ResponseEntity<>("Delete!",HttpStatus.OK);
    }
}
