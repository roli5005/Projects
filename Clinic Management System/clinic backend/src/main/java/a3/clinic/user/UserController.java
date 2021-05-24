package a3.clinic.user;

import a3.clinic.user.dto.UserCreateDTO;
import a3.clinic.user.dto.UserMinimalDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static a3.clinic.UrlMapping.*;

@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserMinimalDTO> allUsers(){
        return userService.allUsersMinimal();
    }
    @GetMapping("/consultations")
    public List<String> allDoctors(){
        return userService.allDoctors();
    }

    @GetMapping(ENTITY)
    public UserCreateDTO getUser(@PathVariable long id){
        return userService.getUser(id);
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable long id){userService.delete(id);}

    @PatchMapping
    public void edit(@RequestBody UserCreateDTO userCreateDto) {
        userService.edit(userCreateDto);
    }

    @PostMapping
    public void create(@RequestBody UserCreateDTO userCreateDto){ userService.create(userCreateDto); }
}
