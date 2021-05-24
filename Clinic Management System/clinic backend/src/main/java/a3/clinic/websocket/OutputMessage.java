package a3.clinic.websocket;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OutputMessage {
    private String message;
    private String doctorName;
}
