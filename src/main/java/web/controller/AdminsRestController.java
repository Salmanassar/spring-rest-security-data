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
        user = getAndPutInformationToUser(user, json);
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
        user = getAndPutInformationToUser(user, json);
        return userService.createUser(user);
    }

    private User getAndPutInformationToUser(User user, JSONObject json) throws JSONException {
        user = User.builder()
                .id(checkIDUser(user, json))
                .firstName(json.getString("first_name"))
                .lastName(json.getString("last_name"))
                .age((byte) json.getInt("age"))
                .email(json.getString("email"))
                .password(json.getString("password"))
                .roles(new Role().setRoleString(json.getString("role")))
                .build();
        return user;
    }

    private Long checkIDUser(User user, JSONObject json) throws JSONException {
        Long id;
        if (json.isNull("idEdit")) {
           id = 0L;
        } else {
            id = json.getLong("idEdit");
        }
        return id;
    }
}
