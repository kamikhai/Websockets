package chat.controller;

import chat.services.AuthService;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import chat.models.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SignUpController {

    @Autowired
    private AuthService authService;


    @PreAuthorize("permitAll()")
    @GetMapping("/signUp")
    public ModelAndView getView(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView();
        if (authentication != null){
            modelAndView.setViewName("redirect:/profile");
        } else {
            modelAndView.setViewName("signup");
        }
        return modelAndView;
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/signUp")
    public String getCourse(@RequestParam String username, @RequestParam String password,
                            @RequestParam String name, HttpServletResponse response) {
        String token = authService.signUp(User.builder()
                .username(username)
                .name(name)
                .password(password)
                .build()).getToken();
        Cookie cookie = new Cookie("X-Authorization", token);
        cookie.setMaxAge(300);
        response.addCookie(cookie);
        return "redirect:/rooms";
    }
}
