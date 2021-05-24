package a3.clinic.consult;

import a3.clinic.consult.dto.ConsultDTO;
import a3.clinic.consult.dto.ConsultDescriptionDTO;
import a3.clinic.consult.mapper.ConsultMapper;
import a3.clinic.consult.model.Consult;
import a3.clinic.consult.validator.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConsultService {
    private final ConsultRepository consultRepository;
    private final ConsultMapper consultMapper;

    public List<ConsultDTO> allConsults(){
        return consultRepository.findAll().stream()
                .map(consultMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ConsultDescriptionDTO> allConsultDescriptions(){
        return consultRepository.findAll().stream()
                .map(consultMapper::toDescriptionDto)
                .collect(Collectors.toList());
    }


    public void delete(long id){consultRepository.deleteById(id);}

    public Notification<ConsultDTO> create(ConsultDTO consultDTO) throws ParseException {
        Notification<ConsultDTO> notification = new Notification<>();
        LocalDate localDate = LocalDate.parse(consultDTO.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalTime time = LocalTime.of(Integer.parseInt(consultDTO.getTime()),0);
        Consult newConsult = Consult.builder()
                .patient_name(consultDTO.getPatient_name())
                .doctor_name(consultDTO.getDoctor_name())
                .date(localDate)
                .time(time)
                .description("")
                .build();
        if(existsConsultForDoctor(consultDTO)) notification.addError("Doctor already has an appointment on "+consultDTO.getDate()+" | "+consultDTO.getTime());
        if(existsConsultForPatient(consultDTO)) notification.addError("Patient already has an appointment on "+consultDTO.getDate()+ " | "+consultDTO.getTime());
        if(!notification.hasErrors()) notification.setResult(consultMapper.toDto(consultRepository.save(newConsult)));
        return notification;
    }

    public Notification<ConsultDTO> edit(ConsultDTO consultDTO){
        LocalDate localDate = LocalDate.parse(consultDTO.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalTime time = LocalTime.of(Integer.parseInt(consultDTO.getTime()),0);
        Consult updateConsult = Consult.builder()
                .patient_name(consultDTO.getPatient_name())
                .doctor_name(consultDTO.getDoctor_name())
                .date(localDate)
                .time(time)
                .description("")
                .build();
        Notification<ConsultDTO> notification = new Notification<>();
        if(existsConsultForDoctor(consultDTO)) notification.addError("Doctor already has an appointment on "+consultDTO.getDate()+" | "+consultDTO.getTime());
        if(existsConsultForPatient(consultDTO)) notification.addError("Patient already has an appointment on "+consultDTO.getDate()+ " | "+consultDTO.getTime());
        if(!notification.hasErrors()) {
            consultRepository.deleteById(consultDTO.getId());
            notification.setResult(consultMapper.toDto(consultRepository.save(updateConsult)));
        }
        return notification;
    }

    public boolean existsConsultForDoctor(ConsultDTO consultDTO){
        Consult consult = Consult.builder()
                .doctor_name(consultDTO.getDoctor_name())
                .date(LocalDate.parse(consultDTO.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .time(LocalTime.of(Integer.parseInt(consultDTO.getTime()),0))
                .build();
        return consultRepository.existsConsultForDoctor(consult.getDoctor_name(), consult.getDate(),consult.getTime()).isPresent();
    }
    public boolean existsConsultForPatient(ConsultDTO consultDTO){
        Consult consult = Consult.builder()
                .patient_name(consultDTO.getDoctor_name())
                .date(LocalDate.parse(consultDTO.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .time(LocalTime.of(Integer.parseInt(consultDTO.getTime()),0))
                .build();
        return consultRepository.existsConsultForPatient(consult.getPatient_name(), consult.getDate(), consult.getTime()).isPresent();
    }

    public ConsultDescriptionDTO addDescription(ConsultDescriptionDTO consultDescriptionDTO){
        Optional<Consult> consult = consultRepository.findById(consultDescriptionDTO.getId());
        consult.get().setDescription(consultDescriptionDTO.getDescription());
        return consultMapper.toDescriptionDto(consultRepository.save(consult.get()));
    }
}
