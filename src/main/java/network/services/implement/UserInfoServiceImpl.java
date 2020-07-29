package network.services.implement;
import network.data.models.UserInfo;
import network.data.repo.UserInfoRepository;
import network.exception.LogicException;
import network.services.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UserInfoServiceImpl implements IUserInfoService {
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public Optional<UserInfo> get(Integer id) {
        return userInfoRepository.findById(id);
    }

    @Override
    public List<UserInfo> getAll() {
        return (List<UserInfo>) userInfoRepository.findAll();
    }

    @Override
    public List<UserInfo> GetFollow(Integer idUser) {
        return userInfoRepository.findAllUserFollow(idUser);
    }

    @Override
    public List<UserInfo> GetByFollow(Integer idUser) {
        return userInfoRepository.findAllUserByFollow(idUser);
    }

    @Override
    public UserInfo save(UserInfo userInfo) {
        String time = java.time.LocalDateTime.now().toString();
        userInfo.setTime_created(time);
        userInfo.setTime_updated(time);
        return userInfoRepository.save(userInfo);
    }

    @Override
    public UserInfo update(UserInfo userInfo) throws Exception {
        Optional<UserInfo> optionalUpdatingUserInfo = userInfoRepository.findById(userInfo.getId());
        if (!optionalUpdatingUserInfo.isPresent()) {
            throw new LogicException("UserInfo khong ton tai", HttpStatus.NOT_FOUND);
        }
        UserInfo updatingUserInfo = optionalUpdatingUserInfo.get();
        if (null != userInfo.getCity())
            updatingUserInfo.setCity(userInfo.getCity());
        if (null != userInfo.getBirth_date())
            updatingUserInfo.setBirth_date(userInfo.getBirth_date());
        if (null != userInfo.getEmail())
            updatingUserInfo.setEmail(userInfo.getEmail());
        if (null != userInfo.getAvatar_picture_url())
            updatingUserInfo.setAvatar_picture_url(userInfo.getAvatar_picture_url());
        if (null != userInfo.getFirst_name())
            updatingUserInfo.setFirst_name(userInfo.getFirst_name());
        if (null != userInfo.getGender())
            updatingUserInfo.setGender(userInfo.getGender());
        if (null != userInfo.getLast_name())
            updatingUserInfo.setLast_name(userInfo.getLast_name());
        updatingUserInfo.setTime_updated(java.time.LocalDateTime.now().toString());

        return userInfoRepository.save(updatingUserInfo);
    }

    @Override
    public void delete(Integer id) {
        userInfoRepository.deleteById(id);
    }
}
