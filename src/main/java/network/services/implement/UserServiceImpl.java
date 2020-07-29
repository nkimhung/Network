package network.services.implement;

import network.data.models.User;
import network.data.models.UserInfo;
import network.data.repo.UserInfoRepository;
import network.data.repo.UserRepository;
import network.exception.LogicException;
import network.services.IUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByUserInfo(Integer UserInfoId) {
        return userRepository.findId(UserInfoId);
    }

    @Override
    public Optional<User> get(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User save(User user) throws LogicException {
        if(user.getUsername()==null || user.getUsername().equals("")){
            throw new LogicException("Username không hợp lệ", HttpStatus.BAD_REQUEST);
        }
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        UserInfo userInfo = new UserInfo();
        userInfo.setAvatar_picture_url("3023580924700user.png");
        userInfo.setLast_name(user.getUsername());
        String time = java.time.LocalDateTime.now().toString();
        userInfo.setTime_created(time);
        userInfo.setTime_updated(time);
        UserInfo userInfo1 =userInfoRepository.save(userInfo);
        int id =userInfo1.getId();

        user.setUsers_info_id(id);
        return userRepository.save(user);
    }

    @Override
    public User update(User user) throws Exception {
        if (null != user.getNewPassword()) {
            user.setPassword(DigestUtils.md5Hex(user.getNewPassword()));
        }
        return userRepository.save(user);
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}
