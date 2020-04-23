package chat.services;

import chat.models.Room;
import chat.repositories.RoomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoomsServiceImpl implements RoomsService {
    @Autowired
    private RoomsRepository roomsRepository;

    @Override
    public List<Room> findAll() {
        return roomsRepository.findAll();
    }
}
