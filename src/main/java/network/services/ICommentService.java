package network.services;

import network.data.models.Comment;

import java.util.List;

public interface ICommentService {
    public List<Comment> getAllByIdPost(Integer IdPost);

    public Comment getByID(Integer id);

    public Comment save(Comment comment);

    public Comment update(Comment comment) throws Exception;

    public void delete(Integer id);
}
