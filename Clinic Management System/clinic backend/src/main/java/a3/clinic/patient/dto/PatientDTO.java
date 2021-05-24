package a3.clinic.patient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {
    private long id;
    private String name;
    private String cardNumber;
    private String personalCode;
    private String dateOfBirth;
    private String address;
}
