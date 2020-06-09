package network.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import network.interceptor.HasRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class TokenManager {

    public static TokenManager tokenManager;
    private Map<String, String> storedTokens = new HashMap<>();

    @Autowired
    private ObjectMapper mapper;

    public static TokenManager getInstance() {
        return tokenManager;
    }

    @PostConstruct
    public void setup() {
        tokenManager = this;

    }

    public String createToken(String username) {
        String uuidToken = UUID.randomUUID().toString();
        storedTokens.put(uuidToken, username);
        return uuidToken;
    }

    public boolean isAuthenticated(HttpServletRequest request) throws JsonProcessingException {
        System.out.println(mapper.writeValueAsString(storedTokens));
        String token = getToken(request);
        return storedTokens.get(token) != null;
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader("AuthToken");
        if (token == null) {
            token = request.getParameter("AuthToken");
        }
        if (token == null) {
            token = (String) request.getSession().getAttribute("AuthToken");
        }
        return token;
    }

    public String getUsername(String token) throws JsonProcessingException {
       return storedTokens.get(token);
    }
}
