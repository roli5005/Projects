package a3.clinic.websocket;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Message {
    private String patient;
    private String date;
    private String time;
    private String doctorName;
}
