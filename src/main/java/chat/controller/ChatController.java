package chat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.util.UUID;

@Controller
public class ChatController {

    @GetMapping("/chat")
    public String getChat(Model model, HttpServletRequest request,
                          @RequestParam Long room, HttpServletResponse response) {
        model.addAttribute("room", room);
        return "chat";
    }
}
