package network.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import network.data.models.User;
import network.data.models.UserInfo;
import network.interceptor.HasRole;
import network.services.IUserInfoService;
import network.services.IUserService;
import network.util.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserInfoController {
    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private IUserService iUserService;



    public UserInfoController() throws JsonProcessingException {
    }
    @GetMapping("/UserInfo")
    @HasRole("User")
    public UserInfo getInfo( @RequestHeader("AuthToken") String token) throws JsonProcessingException {
        String userName= TokenManager.getInstance().getUsername(token);
        Optional<User> userInfo1= iUserService.findByUsername(userName);
        int id =userInfo1.get().getUsers_info_id();
        return userInfoService.get(id).get();
    }
    @GetMapping("/UserInfo/{idInfo}")
    @HasRole("User")
    public UserInfo getInfoOf(@PathVariable("idInfo") Integer userIdInfo) throws JsonProcessingException {
        return userInfoService.get(userIdInfo).get();
    }
    @GetMapping("/UserFollow")
    @HasRole("User")
    public ResponseEntity<List<UserInfo>> getUserFollow( @RequestHeader("AuthToken") String token) throws JsonProcessingException {
        String userName= TokenManager.getInstance().getUsername(token);
        Optional<User> userInfo1= iUserService.findByUsername(userName);
        int id =userInfo1.get().getId();
        return new ResponseEntity<>(userInfoService.GetFollow(id),HttpStatus.OK);
    }
    @GetMapping("/UserFollow/{idInfo}")
    @HasRole("User")
    public ResponseEntity<List<UserInfo>> getUserFollowForUser(@PathVariable("idInfo") Integer userIdInfo, @RequestHeader("AuthToken") String token) throws JsonProcessingException {
        Optional<User> userInfo1= iUserService.findByUserInfo(userIdInfo);
        int id =userInfo1.get().getId();
        return new ResponseEntity<>(userInfoService.GetFollow(id),HttpStatus.OK);
    }
    @GetMapping("/UserByFollow")
    @HasRole("User")
    public ResponseEntity<List<UserInfo>> getUserByFollow( @RequestHeader("AuthToken") String token) throws JsonProcessingException {
        String userName= TokenManager.getInstance().getUsername(token);
        Optional<User> userInfo1= iUserService.findByUsername(userName);
        int id =userInfo1.get().getId();
        return new ResponseEntity<>(userInfoService.GetByFollow(id),HttpStatus.OK);
    }
    @GetMapping("/UserByFollow/{idInfo}")
    @HasRole("User")
    public ResponseEntity<List<UserInfo>> getUserByFollowUser(@PathVariable("idInfo") Integer userIdInfo) throws JsonProcessingException {
        Optional<User> userInfo1= iUserService.findByUserInfo(userIdInfo);
        int id =userInfo1.get().getId();
        return new ResponseEntity<>(userInfoService.GetByFollow(id),HttpStatus.OK);
    }
    @GetMapping("/AllUserInfo")
    @HasRole("User")
    public ResponseEntity<List<UserInfo>> getAll( @RequestHeader("AuthToken") String token) throws JsonProcessingException {
        return new ResponseEntity<>(userInfoService.getAll(),HttpStatus.OK);
    }
    @PutMapping("/UserInfo")
    @HasRole("User")
    public ResponseEntity<String> updateUserInfo(@RequestBody UserInfo userInfo, @RequestHeader("AuthToken") String token) throws Exception {

            String userName= TokenManager.getInstance().getUsername(token);
            Optional<User> userInfo1= iUserService.findByUsername(userName);
            int id =userInfo1.get().getUsers_info_id();
            Optional<UserInfo> currentUserInfo = userInfoService.get(id);

            if (currentUserInfo == null) {
                return new ResponseEntity<>("Khong tim duoc UserInfo",HttpStatus.NOT_FOUND);
            }
            userInfo.setId(id);
            UserInfo userInfo2= userInfoService.update(userInfo);
            return new ResponseEntity<>("Updated!", HttpStatus.OK);


    }

    @PostMapping("/UserInfo")
    public ResponseEntity<Integer> AddUserInfo(@RequestBody UserInfo userInfo) throws Exception {

        if (userInfo == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserInfo userInfo1=userInfoService.save(userInfo);
        return new ResponseEntity<>(userInfo1.getId(), HttpStatus.CREATED);
    }
}
