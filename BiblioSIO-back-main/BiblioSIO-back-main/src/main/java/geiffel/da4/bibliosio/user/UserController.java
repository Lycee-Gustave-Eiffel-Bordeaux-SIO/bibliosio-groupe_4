package geiffel.da4.bibliosio.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    public UserController(@Qualifier("jpaUsers") UserService userService)
    {
        this.userService=userService;
    }

    @GetMapping("")
    public List<User> getAll()
    {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id)
    {
        return userService.getById(id);
    }

    @PostMapping("")
    public ResponseEntity createUser(@RequestBody User user)
    {
        User createdUser = userService.create(user);
        return ResponseEntity.created(URI.create("/users/"+createdUser.getId().toString())).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity updateUser(@PathVariable Long id, @RequestBody User user)
    {
        userService.update(id, user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id)
    {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
