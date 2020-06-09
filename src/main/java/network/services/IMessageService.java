package network.services;

import network.data.models.Like;
import network.data.models.Message;
import network.dto.messages;

import java.util.List;
import java.util.Optional;

public interface IMessageService {
    public List<messages> getMessageFrom(Integer userIdFrom);
    public List<messages> getMessageTo(Integer userIdTo);
    public List<messages> getMessage(Integer userIdFrom,Integer userIdTo);
    public Message save(Message message);
    public Optional<Message> getMessage (Integer id);

    public void delete(Integer id);
}
