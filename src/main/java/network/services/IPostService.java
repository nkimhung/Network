package network.services;

import network.data.models.Post;

import java.util.List;
import java.util.Optional;

public interface IPostService {
    public List<Post> getAll();
    public List<Post> getAllFollow(Integer userId);
    public List<Post> getAllFollowByUser(Integer userId,Integer userIdFollow);
    public List<Post> getAllMyPost(Integer userId);
    public List<String> getAllMyImage(Integer userId);

    public Post getByID(Integer id);

    public Post save(Post post);

    public Post update(Post post) throws Exception;

    public void delete(Integer id);
}
