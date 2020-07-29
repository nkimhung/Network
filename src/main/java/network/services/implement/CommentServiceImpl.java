package network.services.implement;

import network.data.models.Comment;
import network.data.models.User;
import network.data.models.UserInfo;
import network.data.repo.CommentRepository;
import network.data.repo.UserInfoRepository;
import network.data.repo.UserRepository;
import network.exception.LogicException;
import network.services.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    public CommentServiceImpl commentService;

    public Comment getCommentFullInfo(Comment comment){
        Optional<User> idUserInfo = userRepository.findById(comment.getUser());
        Optional<UserInfo> userInfo =userInfoRepository.findById(idUserInfo.get().getUsers_info_id());
        comment.setUserInfo(userInfo.get());
        return comment;
    }

    @Override
    public List<Comment> getAllByIdPost(Integer idPost) {
        List<Comment>  listComment= commentRepository.findByPost(idPost);
        int index=0;
        for (Comment comment: listComment
        ) {
            Comment comment1 =commentService.getCommentFullInfo(comment);
            listComment.set(index,comment1);
            index++;

        }
        return listComment;
    }

    @Override
    public Comment getByID(Integer id) {
        return commentService.getCommentFullInfo(commentRepository.findById(id).get());
    }

    @Override
    public Comment save(Comment comment) {
        LocalDateTime time = java.time.LocalDateTime.now();
        comment.setTimeCreated(time);
        comment.setTimeUpdated(time);
        return commentRepository.save(comment);
    }

    @Override
    public Comment update(Comment comment) throws Exception {
            Optional<Comment> optionalUpdatingComment = commentRepository.findById(comment.getId());
            if (!optionalUpdatingComment.isPresent()) {
                throw new LogicException("Comment khong ton tai", HttpStatus.NOT_FOUND);
            }
            Comment updatingComment = optionalUpdatingComment.get();
            if (null != comment.getContent())
                updatingComment.setContent(comment.getContent());
            updatingComment.setTimeUpdated(java.time.LocalDateTime.now());
            return commentRepository.save(updatingComment);
        }

    @Override
    public void delete(Integer id) {
        commentRepository.deleteById(id);
    }
}
