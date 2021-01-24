package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import web.model.Role;
import web.model.User;
import web.service.UserService;

@RestController
class RegistrationController {

    private UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public UserService getUserService() {
        return userService;
    }

    @PostMapping(value = "register")
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
