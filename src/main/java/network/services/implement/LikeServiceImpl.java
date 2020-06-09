package network.services.implement;

import network.data.models.Like;
import network.data.models.User;
import network.data.models.UserInfo;
import network.data.repo.LikeRepository;
import network.data.repo.UserInfoRepository;
import network.data.repo.UserRepository;
import network.exception.LogicException;
import network.services.ILikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LikeServiceImpl implements ILikeService {
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    public LikeServiceImpl likeService;

    @Override
    public List<Like> getAllLikeByIdPost(Integer idPost) {

        List<Like>  likeList= likeRepository.findByPost(idPost);
        int index=0;
        for (Like like: likeList
             ) {
            Like like1 =likeService.getLikeFullInfo(like);
            likeList.set(index,like1);
            index++;

        }
        return likeList;
    }

    @Override
    public List<Like> checkLike(Integer user, Integer post) {
        return likeRepository.findAllByUserAndPost(user,post);
    }

    @Override
    public Like getByID(Integer id) {
        return likeService.getLikeFullInfo(likeRepository.findById(id).get());
    }
    public Like getLikeFullInfo(Like like){
        Optional<User> idUserInfo = userRepository.findById(like.getUser());
        Optional<UserInfo> userInfo =userInfoRepository.findById(idUserInfo.get().getUsers_info_id());
        like.setUserInfo(userInfo.get());
        return like;
    }
    @Override
    public Like save(Like like) throws LogicException {
        if(likeRepository.findAllByUserAndPost(like.getUser(),like.getPost()).isEmpty()) {
            String time = java.time.LocalDateTime.now().toString();
            like.setTime_created(time);
            return likeRepository.save(like);
        }
        throw new LogicException("Da like roi khong the like tiep", HttpStatus.BAD_REQUEST);
    }

    @Override
    public void delete(Integer id) {
        likeRepository.deleteById(id);
    }
}
