package a3.clinic.security;


import a3.clinic.security.dto.SignupRequest;
import a3.clinic.user.RoleRepository;
import a3.clinic.user.UserRepository;
import a3.clinic.user.model.ERole;
import a3.clinic.user.model.Role;
import a3.clinic.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;


    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void register(SignupRequest signUpRequest) {
        User user = User.builder()
                .username(signUpRequest.getUsername())
                .password(encoder.encode(signUpRequest.getPassword()))
                .email(signUpRequest.getEmail())
                .build();

        String rolesStr = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();


            Role role = roleRepository.findByName(ERole.valueOf(rolesStr))
                    .orElseThrow(() -> new RuntimeException("Cannot find role"));
            roles.add(role);

        user.setRoles(roles);
        userRepository.save(user);
    }
}
