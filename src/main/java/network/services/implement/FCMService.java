package network.services.implement;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class FCMService {
    public String pushNotification(network.data.models.Message messages,String token) {
        Message message = Message.builder()
                .putData("content",messages.getContent())
                .setToken(token)
                .build();

        String response = null;
        try {
            response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
        return response;
    }
}
