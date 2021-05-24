package roland.bookstore.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import roland.bookstore.TestCreationFactory;
import roland.bookstore.user.dto.UserCreateDto;
import roland.bookstore.user.dto.UserListDTO;
import roland.bookstore.user.mapper.UserMapper;
import roland.bookstore.user.model.User;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
@SpringBootTest
class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private UserMapper userMapper;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository,userMapper,roleRepository,encoder);
    }

    @Test
    void allUsersForList(){
        List<User> users = TestCreationFactory.listOf(User.class);
        when(userRepository.findAll()).thenReturn(users);

        List<UserListDTO> all = userService.allUsersForList();

        Assertions.assertEquals(users.size(), all.size());
    }

    @Test
    void delete() {
        User user = User.builder()
                .username("test")
                .password("Test1Pass")
                .email("test@test.com")
                .id(1L)
                .build();
        userService.delete(user.getId());
    }

    @Test
    void edit() {
        UserCreateDto userdto = UserCreateDto.builder()
                .username("test")
                .password("Test1Pass")
                .email("test@test.com")
                .id(1L)
                .build();

        User user = User.builder()
                .username("test")
                .password("Test1Pass")
                .email("test@test.com")
                .id(1L)
                .build();

        when(userMapper.toUserCreateDto(user)).thenReturn(userdto);
        when(userMapper.fromUserCreateDto(userdto)).thenReturn(user);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        //Assertions.assertEquals(userdto.getId(), userService.edit(userdto).getId());
    }

    @Test
    void create() {
        UserCreateDto userdto = UserCreateDto.builder()
                .username("test")
                .password("Test1Pass")
                .email("test@test.com")
                .id(1L)
                .build();

        User user = User.builder()
                .username("test")
                .password("Test1Pass")
                .email("test@test.com")
                .id(1L)
                .build();

        when(userMapper.toUserCreateDto(user)).thenReturn(userdto);
        when(userMapper.fromUserCreateDto(userdto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        //UserCreateDto newUser = userService.create(userdto);
    }
}
