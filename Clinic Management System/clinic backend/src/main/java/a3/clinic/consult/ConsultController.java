package a3.clinic.consult;
import a3.clinic.consult.dto.ConsultDTO;
import a3.clinic.consult.dto.ConsultDescriptionDTO;
import a3.clinic.consult.validator.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

import static a3.clinic.UrlMapping.*;

@RestController
@RequestMapping(CONSULTATIONS)
@RequiredArgsConstructor
public class ConsultController {
    private final ConsultService consultService;

    @GetMapping
    public List<ConsultDTO> allConsultations(){ return consultService.allConsults();}

    @GetMapping(CONSULTATION_DESCRIPTION)
    public List<ConsultDTO> allConsultationsDoctor(){ return consultService.allConsults();}

    @PatchMapping(CONSULTATION_DESCRIPTION)
    public ConsultDescriptionDTO updateDescription(@RequestBody ConsultDescriptionDTO consultDescriptionDTO){
        return consultService.addDescription(consultDescriptionDTO);
    }

    @PostMapping
    public String create(@RequestBody ConsultDTO consultDTO) throws ParseException {
        Notification<ConsultDTO> notification = consultService.create(consultDTO);
        if(notification.hasErrors()) return notification.getFormattedErrors();
        else return "Appointment successfully registered!";
    }

    @PatchMapping
    public String edit(@RequestBody ConsultDTO consultDTO){
        Notification<ConsultDTO> notification = consultService.edit(consultDTO);
        if(notification.hasErrors()) return notification.getFormattedErrors();
        else return "Appointment successfully updated!";
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable long id){consultService.delete(id);}
}
