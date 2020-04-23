package chat.aspects;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

@Aspect
@Component
public class AuthAspect {
    private boolean forbidden;
    @Value("${jwt.secret}")
    private String secret;

    @Pointcut("execution(* chat.controller.ChatController.getChat(..)) || execution(* chat.controller.RoomsController.getRooms(..))")
    public void selectAllMethodsAvaliable() {

    }

    @Around("selectAllMethodsAvaliable())")
    public Object logServiceMethods(ProceedingJoinPoint jp) throws Throwable {
        forbidden = false;
        Object result = jp.proceed(jp.getArgs());
        if (!forbidden){
            return result;
        }
        return "forbidden";
    }


    @Before("selectAllMethodsAvaliable() && args(*, request, *, response)")
    public void checkAuth(HttpServletRequest request,
                          HttpServletResponse response){
        Cookie cookie = WebUtils.getCookie(request, "X-Authorization");
        if (cookie != null) {
            Claims claims = null;
            String token = cookie.getValue();
            try {
                claims = Jwts.parser()
                        .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
                        .parseClaimsJws(token).getBody();

            } catch (JwtException ex) {
                forbidden = true;
                response.setStatus(403);
                System.out.println("Неверный токен");
            }
        } else {
            forbidden = true;
            response.setStatus(403);
        }
    }
}
