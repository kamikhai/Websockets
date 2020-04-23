package chat.services;

import chat.models.Room;

import java.util.List;

public interface RoomsService{
    List<Room> findAll();
}
