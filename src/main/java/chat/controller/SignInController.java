package chat.controller;

import chat.models.User;
import chat.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SignInController {

    @Autowired
    private AuthService authService;


    @GetMapping("/signIn")
    public ModelAndView getView(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView();
        if (authentication == null){
            modelAndView.setViewName("signin");
        } else {
            modelAndView.setViewName("redirect:/index");
        }
        return modelAndView;
    }

    @PostMapping("/signIn")
    public String signIn(@RequestParam String username, @RequestParam String password,
                         HttpServletResponse response, Model model){
        String token = authService.signIn(User.builder()
                .username(username)
                .password(password)
                .build()).getToken();
        if (token != null){
            Cookie cookie = new Cookie("X-Authorization", token);
            cookie.setMaxAge(180);
            response.addCookie(cookie);
            return "redirect:/rooms";
        }
        model.addAttribute("error", "Invalid email or password");
        return "redirect:/signIn";
    }

}
