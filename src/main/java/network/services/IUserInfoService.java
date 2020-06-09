package network.services;

import network.data.models.UserInfo;

import java.util.List;
import java.util.Optional;

public interface IUserInfoService {
    public Optional<UserInfo> get(Integer id);

    public List<UserInfo> getAll();

    public UserInfo save(UserInfo userInfo);

    public UserInfo update(UserInfo userInfo) throws Exception;

    public void delete(Integer id);
}
