package a3.clinic;


import a3.clinic.consult.ConsultRepository;
import a3.clinic.patient.PatientRepository;
import a3.clinic.security.AuthService;
import a3.clinic.security.dto.SignupRequest;
import a3.clinic.user.RoleRepository;
import a3.clinic.user.UserRepository;
import a3.clinic.user.model.ERole;
import a3.clinic.user.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final ConsultRepository consultRepository;

    private final PatientRepository patientRepository;

    private final AuthService authService;


    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {
            userRepository.deleteAll();
            roleRepository.deleteAll();
            consultRepository.deleteAll();
            patientRepository.deleteAll();

            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }
            authService.register(SignupRequest.builder()
                    .email("roland@email.com")
                    .username("Roland")
                    .password("WooHoo1!")
                    .roles("ADMIN")
                    .build());
            authService.register(SignupRequest.builder()
                    .email("roland1@email.com")
                    .password("WooHoo1!")
                    .username("Whatever")
                    .roles("SECRETARY")
                    .build());
            authService.register(SignupRequest.builder()
                    .email("roland3@email.com")
                    .password("WooHoo1!")
                    .username("Someone")
                    .roles("DOCTOR")
                    .build());
        }
    }
}
