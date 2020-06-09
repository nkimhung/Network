package network.services;
import network.data.models.Like;
import network.exception.LogicException;

import java.util.List;
import java.util.Optional;

public interface ILikeService {
    public List<Like> getAllLikeByIdPost(Integer IdPost);
    public List<Like> checkLike(Integer user,Integer post);

    public Like getByID(Integer id);

    public Like save(Like like) throws LogicException;

    public void delete(Integer id);
}
