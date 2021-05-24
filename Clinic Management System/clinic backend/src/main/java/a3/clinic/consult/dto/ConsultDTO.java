package a3.clinic.consult.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsultDTO {
    private long id;
    private String patient_name;
    private String doctor_name;
    private String date;
    private String time;
    private String description;
}
