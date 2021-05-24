package roland.bookstore.user;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import roland.bookstore.user.dto.UserCreateDto;
import roland.bookstore.user.dto.UserListDTO;

import java.util.List;

import static roland.bookstore.UrlMapping.ENTITY;
import static roland.bookstore.UrlMapping.USERS;

@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserListDTO> allUsers() {
        return userService.allUsersForList();
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable long id){userService.delete(id);}

    @PatchMapping
    public void edit(@RequestBody UserCreateDto userCreateDto) {
        userService.edit(userCreateDto);
    }

    @PostMapping
    public void create(@RequestBody UserCreateDto userCreateDto){ userService.create(userCreateDto); }


}
