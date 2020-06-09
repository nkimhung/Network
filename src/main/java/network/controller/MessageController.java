package network.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import network.data.models.Message;
import network.data.models.User;
import network.dto.MessageResponse;
import network.dto.messages;
import network.interceptor.HasRole;
import network.services.IMessageService;
import network.services.IUserService;
import network.util.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MessageController {
    @Autowired
    private IMessageService iMessageService;
    @Autowired
    private IUserService iUserService;
    @GetMapping("/GetAllMessage")
    @HasRole("User")
    public ResponseEntity<MessageResponse> GetMessageByUser( @RequestHeader("AuthToken") String token) throws JsonProcessingException {
        MessageResponse messageResponse = new MessageResponse();
        Integer idUser =this.getIdUser(token);
        messageResponse.setListMessageFrom(iMessageService.getMessageFrom(idUser));
        messageResponse.setListMessageTo(iMessageService.getMessageTo(idUser));

        return new ResponseEntity<>( messageResponse, HttpStatus.OK);
    }
    @HasRole("User")
    @GetMapping("/GetMessage/{userIdTo}")
    public ResponseEntity<List<messages>> GetMessageWithUserTo(@PathVariable("userIdTo") Integer userIdTo, @RequestHeader("AuthToken") String token) throws JsonProcessingException {

        return new ResponseEntity<>( iMessageService.getMessage(this.getIdUser(token),userIdTo), HttpStatus.OK);
    }
    @HasRole("User")
    @PostMapping("/Message/{userIdTo}")
    public ResponseEntity<String> AddMessage(@PathVariable("userIdTo") Integer userIdTo, @RequestHeader("AuthToken") String token,@RequestBody Message message) throws JsonProcessingException {
        message.setUserIdTo(userIdTo);
        message.setUserIdFrom(this.getIdUser(token));
        iMessageService.save(message);
        return new ResponseEntity<>("Create",HttpStatus.OK);

    }
    @HasRole("User")
    @DeleteMapping("/Message/{id}")
    public ResponseEntity<String> DeleteMessage(@PathVariable("id") Integer id, @RequestHeader("AuthToken") String token) throws JsonProcessingException{

        Optional<Message> message1 =iMessageService.getMessage(id);
        if(message1.get().getUserIdFrom()!=this.getIdUser(token)){
            return new ResponseEntity<>("Bạn không có quyền thực hiện xóa Message cua người khác",HttpStatus.UNAUTHORIZED);
        }
        iMessageService.delete(id);
        return new ResponseEntity<>("Delete",HttpStatus.OK);

    }
    public int getIdUser(String token) throws JsonProcessingException {
        String userName= TokenManager.getInstance().getUsername(token);
        Optional<User> user= iUserService.findByUsername(userName);
        return user.get().getId();
    }

}
