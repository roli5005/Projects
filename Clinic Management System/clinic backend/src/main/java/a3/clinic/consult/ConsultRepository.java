package a3.clinic.consult;

import a3.clinic.consult.model.Consult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface ConsultRepository extends JpaRepository<Consult,Long> {

    @Query("SELECT c FROM Consult c WHERE c.doctor_name = ?1 and c.date = ?2 and c.time = ?3")
    Optional<Consult> existsConsultForDoctor(String name, LocalDate date, LocalTime time);

    @Query("SELECT c FROM Consult c WHERE c.patient_name = ?1 and c.date = ?2 and c.time = ?3")
    Optional<Consult> existsConsultForPatient(String name, LocalDate date, LocalTime time);
}
