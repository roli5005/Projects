package roland.bookstore.security;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import roland.bookstore.security.dto.SignupRequest;
import roland.bookstore.user.RoleRepository;
import roland.bookstore.user.UserRepository;
import roland.bookstore.user.dto.UserCreateDto;
import roland.bookstore.user.model.ERole;
import roland.bookstore.user.model.Role;
import roland.bookstore.user.model.User;

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

        Set<String> rolesStr = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (rolesStr == null) {
            Role defaultRole = roleRepository.findByName(ERole.CUSTOMER)
                    .orElseThrow(() -> new RuntimeException("Cannot find CUSTOMER role"));
            roles.add(defaultRole);
        } else {
            rolesStr.forEach(r -> {
                Role ro = roleRepository.findByName(ERole.valueOf(r))
                        .orElseThrow(() -> new RuntimeException("Cannot find role: " + r));
                roles.add(ro);
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
    }

    public void manualRegister(UserCreateDto userCreateDto){
        User user = User.builder()
                .username(userCreateDto.getUsername())
                .email(userCreateDto.getEmail())
                .password(encoder.encode( userCreateDto.getPassword()) )
                .build();

        Set<Role> roles = new HashSet<>();
        Role defaultRole = roleRepository.findByName(ERole.CUSTOMER)
                .orElseThrow(() -> new RuntimeException("Cannot find CUSTOMER role"));
        roles.add(defaultRole);

        user.setRoles(roles);

        userRepository.save(user);
    }

    public void updateCredentials(UserCreateDto userCreateDto){
        User updatedUser = User.builder()
                .username(userCreateDto.getUsername())
                .email(userCreateDto.getEmail())
                .password(encoder.encode( userCreateDto.getPassword()) )
                .build();

        Set<Role> roles = new HashSet<>();
        Role defaultRole = roleRepository.findByName(ERole.CUSTOMER)
                .orElseThrow(() -> new RuntimeException("Cannot find CUSTOMER role"));
        roles.add(defaultRole);

        updatedUser.setRoles(roles);
        userRepository.delete(userRepository.findById(userCreateDto.getId()).get());
        userRepository.save(updatedUser);
    }
}
