package a3.clinic.consult.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Consult {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String patient_name;

    @Column(nullable = false)
    private String doctor_name;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime time;

    @Column(nullable = false, length = 520)
    private String description;
}
