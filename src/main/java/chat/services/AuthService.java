package chat.services;

import chat.dto.TokenDto;
import chat.models.User;

public interface AuthService {
    TokenDto signUp(User user);

    TokenDto signIn(User user);

    TokenDto getToken(User user);
}
