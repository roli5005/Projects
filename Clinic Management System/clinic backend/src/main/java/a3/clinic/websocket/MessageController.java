package a3.clinic.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

import static a3.clinic.UrlMapping.NOTIFICATION;


public class MessageController {
    @MessageMapping("/notify")
    @SendTo(NOTIFICATION)
    public OutputMessage sendCheckin(Message message) throws Exception {
        return new OutputMessage("Patient "+message.getPatient()+",arrived for the consultation!", message.getDoctorName());
    }
    @MessageMapping("/notify")
    @SendTo(NOTIFICATION)
    public OutputMessage sendNewConsult(Message message) throws Exception {
        return new OutputMessage("Patient "+message.getPatient()+",has been appointed for consultation! Date: "+message.getDate()
                +" ,Time: "+message.getTime()+" .", message.getDoctorName());
    }
}
