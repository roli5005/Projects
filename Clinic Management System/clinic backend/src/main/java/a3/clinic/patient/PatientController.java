package a3.clinic.patient;
import a3.clinic.patient.dto.PatientDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static a3.clinic.UrlMapping.*;

@RestController
@RequestMapping(PATIENTS)
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @GetMapping
    public List<PatientDTO> allPatients(){ return patientService.allPatients(); }

    @GetMapping("/all")
    public List<String> allPatientsName(){
        return patientService.allPatientNames();}

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable long id){ patientService.delete(id);}

    @PatchMapping
    public PatientDTO edit(@RequestBody PatientDTO patientDTO){ return patientService.edit(patientDTO); }

    @PostMapping
    public PatientDTO create(@RequestBody PatientDTO patientDTO){ return patientService.create(patientDTO); }



}
