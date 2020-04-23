package chat.controller;

import chat.models.Room;
import chat.services.RoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoomsRestController {

    @Autowired
    private RoomsService roomsService;

    @GetMapping("/api/rooms")
    public ResponseEntity<List<Room>> getRooms(){
        return ResponseEntity.ok(roomsService.findAll());
    }
}
