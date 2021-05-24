package a3.clinic.user.mapper;

import a3.clinic.user.dto.UserCreateDTO;
import a3.clinic.user.dto.UserDetailsImpl;
import a3.clinic.user.dto.UserMinimalDTO;
import a3.clinic.user.model.User;
import org.mapstruct.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "username", source = "user.username")
    })
    UserMinimalDTO userMinimalFromUser(User user);

    User fromDetailsDto(UserDetailsImpl userDetails);

    User fromDto(UserCreateDTO userCreateDTO);

    User fromUserCreateDto(UserCreateDTO userCreateDto);

    UserCreateDTO toDto(User user);

    @Mappings({
            @Mapping(target = "username", source = "user.username"),
            @Mapping(target = "role", ignore = true)
    })
    UserMinimalDTO userMinimalDtoFromUser(User user);

    @AfterMapping
    default void populateRoles(User user, @MappingTarget UserMinimalDTO userMinimalDTO) {
        userMinimalDTO.setRole(user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet()).toString());
    }
}
