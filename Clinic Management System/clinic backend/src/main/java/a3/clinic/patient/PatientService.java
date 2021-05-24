package a3.clinic.patient;

import a3.clinic.patient.dto.PatientDTO;
import a3.clinic.patient.mapper.PatientMapper;
import a3.clinic.patient.model.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public List<String> allPatientNames(){
        return patientRepository.findAll().stream()
                .map(Patient::getName)
                .collect(Collectors.toList());
    }

    public List<PatientDTO> allPatients(){
        return patientRepository.findAll().stream()
                .map(patientMapper::toDto)
                .collect(Collectors.toList());
    }

    public PatientDTO create(PatientDTO patientDTO){
        System.out.print(patientDTO);
        Patient newPatient = Patient.builder()
                .address(patientDTO.getAddress())
                .cardNumber(patientDTO.getCardNumber())
                .name(patientDTO.getName())
                .personalCode(patientDTO.getPersonalCode())
                .dateOfBirth(LocalDate.parse(patientDTO.getDateOfBirth(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .build();
        return patientMapper.toDto(patientRepository.save(newPatient));
    }
    public PatientDTO edit(PatientDTO patientDTO){
        System.out.print(patientDTO);
        Optional<Patient> updatePatient = patientRepository.findById(patientDTO.getId());
        updatePatient.get().setAddress(patientDTO.getAddress());
        updatePatient.get().setCardNumber(patientDTO.getCardNumber());
        updatePatient.get().setName(patientDTO.getName());
        updatePatient.get().setPersonalCode(patientDTO.getPersonalCode());
        updatePatient.get().setDateOfBirth(LocalDate.parse(patientDTO.getDateOfBirth(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        return patientMapper.toDto(patientRepository.save(updatePatient.get()));
    }

    public void delete(long id){patientRepository.deleteById(id);}

}
