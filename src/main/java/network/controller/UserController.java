package network.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import network.data.models.User;
import network.interceptor.HasRole;
import network.services.IUserInfoService;
import network.services.IUserService;
import network.util.TokenManager;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IUserInfoService iUserInfoService;

    public int getIdUser(String token) throws JsonProcessingException {
        String userName= TokenManager.getInstance().getUsername(token);
        Optional<User> user= iUserService.findByUsername(userName);
        return user.get().getId();
    }

    @GetMapping("/User")
    @HasRole("User")
    public User GetUser(@RequestHeader("AuthToken") String token) throws Exception{
        User user = iUserService.get(this.getIdUser(token)).get();
        return user;
    }
    @PutMapping("/User")
    @HasRole("User")
    public ResponseEntity<String> updateUser(@RequestBody User user, @RequestHeader("AuthToken") String token) throws Exception {


        Optional<User> currentUser = iUserService.get(user.getId());
        if(currentUser.get().getPassword().equals(DigestUtils.md5Hex(user.getPassword()))){
            User user1= iUserService.update(user);
            return new ResponseEntity<>("Updated!", HttpStatus.OK);
        }else
            return new ResponseEntity<>("Sai password",HttpStatus.NOT_FOUND);
    }
    @PostMapping("/User")
    public ResponseEntity<String> AddUser(@RequestBody User user) throws Exception {

        Optional<User> currentUser = iUserService.findByUsername(user.getUsername());
        if(currentUser.isPresent()){
            return new ResponseEntity<>("username da ton tai",HttpStatus.NOT_ACCEPTABLE);
        }
        if(iUserInfoService.get(user.getUsers_info_id()).isPresent()) {
            iUserService.save(user);
            return new ResponseEntity<>("Create!", HttpStatus.OK);
        }else
            return new ResponseEntity<>("Không tim thay userInfo",HttpStatus.BAD_REQUEST);

    }
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        Long time = System.nanoTime();
        File convertFile = new File("./uploadfolder/image/" +time+ file.getOriginalFilename());
        convertFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(convertFile);
        fout.write(file.getBytes());
        fout.close();
        return new ResponseEntity<>(time+file.getOriginalFilename(), HttpStatus.OK);
    }
}
