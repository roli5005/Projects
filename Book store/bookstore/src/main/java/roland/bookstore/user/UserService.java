package roland.bookstore.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import roland.bookstore.user.dto.UserCreateDto;
import roland.bookstore.user.dto.UserListDTO;
import roland.bookstore.user.dto.UserMinimalDTO;
import roland.bookstore.user.mapper.UserMapper;
import roland.bookstore.user.model.ERole;
import roland.bookstore.user.model.Role;
import roland.bookstore.user.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    public List<UserMinimalDTO> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(toList());
    }

    public List<UserListDTO> allUsersForList() {
        return userRepository.findAll()
                .stream().map(userMapper::userListDtoFromUser)
                .collect(toList());
    }


    public void delete(long id){
        userRepository.deleteById(id);
    }

    public UserCreateDto edit(UserCreateDto userCreateDto){
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
        return userMapper.toUserCreateDto(userRepository.save(updatedUser));

    }

    public UserCreateDto create(UserCreateDto userCreateDto){
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

        return userMapper.toUserCreateDto(userRepository.save(user));
    }


}
