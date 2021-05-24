package a3.clinic.patient.mapper;

import a3.clinic.patient.dto.PatientDTO;
import a3.clinic.patient.model.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    PatientDTO toDto(Patient patient);

    Patient fromDto(PatientDTO patientDTO);
}
