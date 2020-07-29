package network.services.implement;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class FCMInitializer {
    @Value("${app.firebase-configuration-fire}")
    private String firebaseConfigPath;

    @PostConstruct
    public void initialize() {
       try {
//            FirebaseOptions options = new FirebaseOptions.Builder()
//                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())).build();
//            if (FirebaseApp.getApps().isEmpty()) {
//                FirebaseApp.initializeApp(options);
//            }
           FileInputStream serviceAccount =
                   new FileInputStream("./network-3da82-firebase-adminsdk-v00z0-8287779304.json");

           FirebaseOptions options = new FirebaseOptions.Builder()
                   .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                   .setDatabaseUrl("https://network-3da82.firebaseio.com")
                   .build();

           FirebaseApp.initializeApp(options);
       } catch (IOException e) {
      }
    }
}
