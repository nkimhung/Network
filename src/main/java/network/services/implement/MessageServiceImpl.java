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
    public List<messages> getMessageFrom(Integer userIdFrom) {
        List<Message> messageList =messageRepository.findAllByUserIdFrom(userIdFrom);
        List<messages> list = new ArrayList<>();
        for (Message message: messageList
             ) {

            list.add(messageService.getLikeFullInfo(message));

        }
         return list;
    }
    @Override
    public List<messages> getMessageTo(Integer userIdFrom) {
        List<Message> messageList =messageRepository.findAllByUserIdTo(userIdFrom);
        List<messages> list = new ArrayList<>();
        for (Message message: messageList
        ) {

            list.add(messageService.getLikeFullInfo(message));

        }
        return list;
    }
    public messages getLikeFullInfo (Message message){

        Optional<User> idUserInfo = userRepository.findById(message.getUserIdTo());

        Optional<UserInfo> userInfo =userInfoRepository.findById(idUserInfo.get().getUsers_info_id());
        messages messages=new messages();
        messages.set_id(1);
        messages.setText(message.getContent());
        messages.setCreatedAt(message.getTime_created());
        user user= new user();
        user.set_id(2);
        user.setName(userInfo.get().getFirst_name()+" "+userInfo.get().getLast_name());
        user.setAvatar(userInfo.get().getAvatar_picture_url());
        messages.setUser(user);
        return messages;
    }

    @Override
    public List<messages> getMessage(Integer userIdFrom, Integer userIdTo) {
        List<Message> messageList = messageRepository.findAllWithUserTO(userIdFrom,userIdTo);
        List<messages> list = new ArrayList<>();
        for (Message message: messageList
        ) {

            list.add(messageService.getLikeFullInfo(message));

        }
        return list;
    }

    @Override
    public Message save(Message message) {
        message.setTime_created(java.time.LocalDateTime.now());
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
