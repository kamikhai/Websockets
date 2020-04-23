package chat.dto;

import lombok.Data;

@Data
public class Message {
    private String text;
    private String from;
    private Long room;
}

