package chat.handlers;

import chat.dto.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.xml.bind.DatatypeConverter;
import java.util.HashMap;
import java.util.Map;

@Component
@EnableWebSocket
public class WebSocketMessagesHandler extends TextWebSocketHandler {

    private static final Map<Long, Map<String, WebSocketSession>> roomsSessions = new HashMap<>();

    @Autowired
    private ObjectMapper objectMapper;
    @Value("${jwt.secret}")
    private String secret;

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String messageText = (String) message.getPayload();
        Message messageFromJson = objectMapper.readValue(messageText, Message.class);
        if (!roomsSessions.containsKey(messageFromJson.getRoom())) {
            roomsSessions.put(messageFromJson.getRoom(), new HashMap<>());
        }
        if (!roomsSessions.get(messageFromJson.getRoom()).containsKey(messageFromJson.getFrom())) {
            roomsSessions.get(messageFromJson.getRoom()).put(messageFromJson.getFrom(), session);
        }
        if (messageFromJson.getText().equals("/Exit")) {
            if (roomsSessions.get(messageFromJson.getRoom()).containsKey(messageFromJson.getFrom())) {
                roomsSessions.get(messageFromJson.getRoom()).remove(messageFromJson.getFrom());
            }
        } else if (!messageFromJson.getText().equals("/Login")) {
            for (WebSocketSession currentSession : roomsSessions.get(messageFromJson.getRoom()).values()) {
                currentSession.sendMessage(message);
            }
        }

    }


    private String getUsername(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
                    .parseClaimsJws(token).getBody();

        } catch (JwtException ex) {
            System.out.println("Неверный токен");
        }
        return claims.get("username").toString();
    }
}

