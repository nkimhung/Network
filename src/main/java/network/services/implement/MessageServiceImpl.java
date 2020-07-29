package network.services.implement;
import network.data.models.Message;
import network.data.models.User;
import network.data.models.UserInfo;
import network.data.repo.MessageRepository;
import network.data.repo.UserInfoRepository;
import network.data.repo.UserRepository;
import network.dto.messages;
import network.dto.user;
import network.services.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MessageServiceImpl implements IMessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    MessageServiceImpl messageService;
    @Override
    public List<UserInfo> getMessageFrom(Integer userIdFrom) {
        List<Message> messageList =messageRepository.findAllByUserIdFrom(userIdFrom);
        List<UserInfo> list = new ArrayList<>();
        for (Message message: messageList
             ) {

            Optional<User> idUserInfo = userRepository.findById(message.getUserIdTo());

            Optional<UserInfo> userInfo =userInfoRepository.findById(idUserInfo.get().getUsers_info_id());
            list.add(userInfo.get());

        }
         return list;
    }
    @Override
    public List<UserInfo> getMessageTo(Integer userIdFrom) {
        List<Message> messageList =messageRepository.findAllByUserIdTo(userIdFrom);
        List<UserInfo> list = new ArrayList<>();
        for (Message message: messageList
        ) {
            Optional<User> idUserInfo = userRepository.findById(message.getUserIdFrom());

            Optional<UserInfo> userInfo =userInfoRepository.findById(idUserInfo.get().getUsers_info_id());
            list.add(userInfo.get());
        }
        return list;
    }

    public messages getLikeFullInfo (Message message,Integer idTo){

        Optional<User> idUserInfo = userRepository.findById(message.getUserIdFrom() );

        Optional<UserInfo> userInfo =userInfoRepository.findById(idUserInfo.get().getUsers_info_id());
        Optional<User> idUserInfo1 = userRepository.findById(message.getUserIdTo() );

        Optional<UserInfo> userInfo1 =userInfoRepository.findById(idUserInfo1.get().getUsers_info_id());
        messages messages=new messages();
        messages.setText(message.getContent());
        messages.setCreatedAt(message.getTime_created());
        user user= new user();

        user.setName(userInfo.get().getFirst_name()+" "+userInfo.get().getLast_name());
        if(idUserInfo.get().getId()==idTo){
            user.setAvatar(userInfo.get().getAvatar_picture_url());
            user.set_id(userInfo.get().getId());
        }
        else {
            user.set_id(userInfo.get().getId());
            user.setAvatar(userInfo1.get().getAvatar_picture_url());
        }
        messages.setUser(user);
        return messages;
    }

    @Override
    public List<messages> getMessage(Integer userIdFrom, Integer userIdTo) {
        List<Message> messageList = messageRepository.findAllWithUserTO(userIdFrom,userIdTo);
        List<messages> list = new ArrayList<>();
        int index =1;
        for (Message message: messageList
        ) {
            messages messages=messageService.getLikeFullInfo(message,userIdTo);
            messages.set_id(index);
            list.add(messages);
            index++;

        }
        return list;
    }

    @Override
    public Message save(Message message) {
        message.setTime_created(java.time.LocalDateTime.now());
        int id = userRepository.findId(message.getUserIdTo()).get().getId();
        message.setUserIdTo(id);
        return messageRepository.save(message);
    }

    @Override
    public Optional<Message> getMessage(Integer id) {
        return messageRepository.findById(id);
    }

    @Override
    public void delete(Integer id) {
        messageRepository.deleteById(id);
    }
}
