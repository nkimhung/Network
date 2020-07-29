package network.services;

import network.data.models.User;
import network.exception.LogicException;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    public Optional<User> findByUsername(String username);
    public Optional<User> findByUserInfo(Integer UserInfoId);

    public Optional<User> get(Integer id);

    public List<User> getAll();

    public User save(User user) throws LogicException;

    public User update(User user) throws Exception;

    public void delete(Integer id);
}
