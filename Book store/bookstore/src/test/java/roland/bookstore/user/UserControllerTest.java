package roland.bookstore.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import roland.bookstore.BaseControllerTest;
import roland.bookstore.TestCreationFactory;
import roland.bookstore.user.dto.UserCreateDto;
import roland.bookstore.user.dto.UserListDTO;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static roland.bookstore.TestCreationFactory.*;


class UserControllerTes extends BaseControllerTest {
    @InjectMocks
    private UserController controller;

    @Mock
    private UserService userService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        controller = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
    @Test
    void allUsers() throws Exception {
        List<UserListDTO> userListDTOs = TestCreationFactory.listOf(UserListDTO.class);
        when(userService.allUsersForList()).thenReturn(userListDTOs);

        ResultActions result = mockMvc.perform(get("/api/users"));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userListDTOs));
    }

    @Test
    void delete() throws Exception {
        long id = randomLong();
        doNothing().when(userService).delete(id);

        ResultActions result = performDeleteWIthPathVariable("/api/users/{id}", id);
        verify(userService, times(1)).delete(id);

        result.andExpect(status().isOk());
    }

    @Test
    void edit() throws Exception {
        UserCreateDto user = UserCreateDto.builder()
                .username(randomString())
                .email(randomEmail())
                .password(randomString())
                .build();
        when(userService.edit(user)).thenReturn(user);
        ResultActions resultActions = performPatchWithRequestBody("/api/users",user);
        resultActions.andExpect(status().isOk());
    }

    @Test
    void create() throws Exception {
        UserCreateDto user = UserCreateDto.builder()
                .username(randomString())
                .email(randomEmail())
                .password(randomString())
                .build();
        when(userService.create(user)).thenReturn(user);
        ResultActions resultActions = performPostWithRequestBody("/api/users",user);
        resultActions.andExpect(status().isOk());
    }
}
