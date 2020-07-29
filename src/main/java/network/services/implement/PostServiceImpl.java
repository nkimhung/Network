package network.services.implement;

import network.data.models.Comment;
import network.data.models.Like;
import network.data.models.Post;
import network.data.models.UserInfo;
import network.data.repo.PostRepository;
import network.data.repo.UserInfoRepository;
import network.data.repo.UserRepository;
import network.exception.LogicException;
import network.services.ICommentService;
import network.services.ILikeService;
import network.services.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostServiceImpl implements IPostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ILikeService iLikeService;
    @Autowired
    private ICommentService iCommentService;
    @Autowired
    private PostServiceImpl postService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Override
    public List<Post> getAll() {
        List<Post>  listPost= (List<Post>) postRepository.findAll();
        int index=0;
        for (Post post: listPost
        ) {
            Post post1 =postService.getPostFullInfo(post);
            listPost.set(index,post1);
            index++;

        }
        return listPost;
    }

    @Override
    public List<Post> getAllFollow(Integer userId) {
        List<Post>  listPost = postRepository.findAllPostFollow(userId);
        int index=0;
        for (Post post: listPost
        ) {
            Post post1 =postService.getPostFullInfo(post);
            listPost.set(index,post1);
            index++;

        }
        return listPost;
    }

    @Override
    public List<Post> getAllFollowByUser(Integer userId, Integer userIdFollow) {
        List<Post>  listPost = postRepository.findAllPostFollowByUser(userId,userIdFollow);
        int index=0;
        for (Post post: listPost
        ) {
            Post post1 =postService.getPostFullInfo(post);
            listPost.set(index,post1);
            index++;

        }
        return listPost;
    }

    @Override
    public List<Post> getAllMyPost(Integer userId) {
        List<Post>  listPost = postRepository.findAllMyPost(userId);
        int index=0;
        for (Post post: listPost
        ) {
            Post post1 =postService.getPostFullInfo(post);
            listPost.set(index,post1);
            index++;

        }
        return listPost;
    }

    @Override
    public List<String> getAllMyImage(Integer userId) {
        List<Post>  listPost = postRepository.findAllMyPost(userId);
        List<String> list = new ArrayList<>();
        for (Post post: listPost
        ) {
            list.add(post.getPost_url());

        }
        return list;
    }

    @Override
    public Post getByID(Integer id) {
        Post post =postRepository.findById(id).get();
        return postService.getPostFullInfo(post);
    }
    public Post getPostFullInfo(Post post){
        UserInfo userInfo =userInfoRepository.findById(userRepository.findById(post.getUser_id()).get().getUsers_info_id()).get();
        List<Like> likeList = iLikeService.getAllLikeByIdPost(post.getId());
        List<Comment> commentList =iCommentService.getAllByIdPost(post.getId());
        post.setUserInfo(userInfo);
        post.setComments(commentList);
        post.setLikes(likeList);
        return post;
    }


    @Override
    public Post save(Post post) {
        LocalDateTime time = java.time.LocalDateTime.now();
        post.setTime_created(time);
        post.setTime_updated(time);
        return postRepository.save(post);
    }

    @Override
    public Post update(Post post) throws Exception {
        Optional<Post> optionalUpdatingPost = postRepository.findById(post.getId());
        if (!optionalUpdatingPost.isPresent()) {
            throw new LogicException("Post khong ton tai", HttpStatus.NOT_FOUND);
        }
        Post updatingPost = optionalUpdatingPost.get();
        if (null != post.getCation())
            updatingPost.setCation(post.getCation());
        if (null != post.getPost_url())
            updatingPost.setPost_url(post.getPost_url());
        if (null != post.getType()){
            updatingPost.setType(post.getType());
        }
        updatingPost.setTime_updated(java.time.LocalDateTime.now());
        return postRepository.save(updatingPost);
    }

    @Override
    public void delete(Integer id) {
        postRepository.deleteById(id);
    }
}
