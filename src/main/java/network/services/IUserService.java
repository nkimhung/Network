package network.services;

import network.data.models.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    public Optional<User> findByUsername(String username);

    public Optional<User> get(Integer id);

    public List<User> getAll();

    public User save(User user);

    public User update(User user) throws Exception;

    public void delete(Integer id);
}
