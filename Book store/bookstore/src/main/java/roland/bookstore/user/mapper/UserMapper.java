package roland.bookstore.user.mapper;


import org.mapstruct.*;
import roland.bookstore.user.dto.UserCreateDto;
import roland.bookstore.user.dto.UserDetailsImpl;
import roland.bookstore.user.dto.UserListDTO;
import roland.bookstore.user.dto.UserMinimalDTO;
import roland.bookstore.user.model.User;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "name", source = "user.username")
    })
    UserMinimalDTO userMinimalFromUser(User user);

    User fromDetailsDto(UserDetailsImpl userDetails);

    User fromUserCreateDto(UserCreateDto userCreateDto);

    UserCreateDto toUserCreateDto(User user);

    @Mappings({
            @Mapping(target = "name", source = "user.username"),
            @Mapping(target = "roles", ignore = true)
    })
    UserListDTO userListDtoFromUser(User user);

    @AfterMapping
    default void populateRoles(User user, @MappingTarget UserListDTO userListDTO) {
        userListDTO.setRoles(user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet()));
    }
}
