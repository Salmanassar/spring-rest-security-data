package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminsRestController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ADMIN ROLE_USER')")
    public List<User> getAllUsers() {
        return userService.listUsers();
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ADMIN ROLE_USER')")
    public User getTheUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/edit/")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ADMIN ROLE_USER')")
    public void update(User user, @RequestBody JSONObject json) throws JSONException {
        Long id = json.getLong("idEdit");
        String firstName = json.getString("first_name");
        String lastName = json.getString("last_name");
        byte age = (byte) json.getInt("age");
        String email = json.getString("email");
        String password = json.getString("password");
        String role = json.getString("role");
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAge(age);
        user.setEmail(email);
        user.setPassword(password);
        user.setRoles(new Role().setRoleString(role));
        userService.updateUser(user);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/delete/{id}")
    public User getForDelete(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ADMIN ROLE_USER')")
    public User create(User user, @RequestBody JSONObject json) throws JSONException {
        String firstName = json.getString("firstName");
        String lastName = json.getString("lastName");
        byte age = (byte) json.getInt("age");
        String email = json.getString("email");
        String password = json.getString("password");
        String role = json.getString("role");
        user.setId(0L);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAge(age);
        user.setEmail(email);
        user.setPassword(password);
        user.setRoles(new Role().setRoleString(role));
        return userService.createUser(user);
    }
}
