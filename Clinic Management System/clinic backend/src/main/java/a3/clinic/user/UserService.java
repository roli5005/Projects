package a3.clinic.user;

import a3.clinic.consult.validator.Notification;
import a3.clinic.user.dto.UserCreateDTO;
import a3.clinic.user.dto.UserMinimalDTO;
import a3.clinic.user.mapper.UserMapper;
import a3.clinic.user.model.ERole;
import a3.clinic.user.model.Role;
import a3.clinic.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;

    public List<UserMinimalDTO> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalDtoFromUser)
                .collect(toList());
    }

    public List<String> allDoctors(){
        List<User> users = userRepository.findAll();
        List<String> doctors = new ArrayList<>();
        for (User u:users
             ) {
            if(userMapper.userMinimalDtoFromUser(u).getRole().equals("[DOCTOR]")) doctors.add(u.getUsername());
        }
        return doctors;
    }

    public void delete(long id){
        userRepository.deleteById(id);
    }

    public Notification<UserCreateDTO> edit(UserCreateDTO userCreateDto){
        Notification<UserCreateDTO> notification = new Notification<>();
        if(!userRepository.existsByEmail(userCreateDto.getEmail()) || !userRepository.existsByUsername(userCreateDto.getUsername())) {
        User updatedUser = User.builder()
                .username(userCreateDto.getUsername())
                .email(userCreateDto.getEmail())
                .password(encoder.encode( userCreateDto.getPassword()) )
                .build();

        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName(ERole.valueOf(userCreateDto.getRole()))
                .orElseThrow(() -> new RuntimeException("Cannot find "+ userCreateDto.getRole()+" role"));
        roles.add(role);

        updatedUser.setRoles(roles);
        userRepository.delete(userRepository.findById(userCreateDto.getId()).get());
        notification.setResult(userMapper.toDto(userRepository.save(updatedUser)));

    }
        else notification.addError("Email and/or Username already exists");
        return notification;

    }

    public Notification<UserCreateDTO> create(UserCreateDTO userCreateDto){
        Notification<UserCreateDTO> notification = new Notification<>();
        if(!userRepository.existsByEmail(userCreateDto.getEmail()) || !userRepository.existsByUsername(userCreateDto.getUsername())) {

            User newUser = User.builder()
                    .username(userCreateDto.getUsername())
                    .email(userCreateDto.getEmail())
                    .password(encoder.encode(userCreateDto.getPassword()))
                    .build();

            Set<Role> roles = new HashSet<>();
            Role role = roleRepository.findByName(ERole.valueOf(userCreateDto.getRole()))
                    .orElseThrow(() -> new RuntimeException("Cannot find " + userCreateDto.getRole() + " role"));
            roles.add(role);

            newUser.setRoles(roles);
            notification.setResult(userMapper.toDto(userRepository.save(newUser)));
        }
        else notification.addError("Email and/or Username already exists");
        return notification;
    }

    public UserCreateDTO getUser(long id){
       return userMapper.toDto(userRepository.findById(id).get());
    }
}
