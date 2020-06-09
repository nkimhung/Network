package network.services.implement;

import network.data.models.User;
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

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
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
    public User save(User user) {

        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User update(User user) throws Exception {
        Optional<User> optionalUpdatingUser = userRepository.findById(user.getId());
        if (!optionalUpdatingUser.isPresent()) {
            throw new LogicException("User không tồn tại", HttpStatus.NOT_FOUND);
        }
        User updatingUser = optionalUpdatingUser.get();
        if (null != user.getNewPassword()) {
            updatingUser.setPassword(DigestUtils.md5Hex(user.getNewPassword()));
        }
        return userRepository.save(updatingUser);
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}
