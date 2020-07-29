package network.controller;
import network.data.models.User;
import network.data.models.UserInfo;
import network.dto.AuthenticationRequest;
import network.exception.LogicException;
import network.services.IUserInfoService;
import network.services.IUserService;
import network.util.TokenManager;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserInfoService userInfoService;

    @RequestMapping("/doLogin")
    @CrossOrigin(origins = "http://localhost:4200")
    @ResponseBody
    public Object doLogin(@RequestBody AuthenticationRequest authenticationRequest
    ) throws LogicException {
        Optional<User> optionalUser = userService.findByUsername(authenticationRequest.getUserName());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String md5Hex = DigestUtils
                    .md5Hex(authenticationRequest.getPassWord()).toUpperCase();
            if (user.getPassword().toUpperCase().equals(md5Hex)) {
                String token = tokenManager.createToken(user.getUsername());
                Optional<UserInfo> userInfo = userInfoService.get(user.getUsers_info_id());
                if (userInfo.get() != null) {
                    userInfo.get().setToken(token);
                }
                return userInfo.get();
            }
            throw new LogicException("PassWord invalid", HttpStatus.NOT_ACCEPTABLE);
            // throw ma exception dang ma http ok: 200, not found: 40..
        }
        throw new LogicException("Username invalid", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/logout")
    public ResponseEntity logout(HttpSession session) {
        session.removeAttribute("role");
        session.removeAttribute("AuthToken");
        session.removeAttribute("Username");
        return ResponseEntity.ok(" ");
    }
}
// set session co 3 thu: role la gi, auth cho user da dang nhap hay chua,
