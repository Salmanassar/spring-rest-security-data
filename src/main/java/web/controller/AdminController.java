package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import web.model.User;
import web.service.UserService;
import web.util.CurrentUser;

import java.security.Principal;
import java.util.List;

@Controller
public class AdminController {

    private UserService userService;

    @Autowired
    private CurrentUser currentUser;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public UserService getUserService() {
        return userService;
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ADMIN ROLE_USER')")
    public ModelAndView startAdmin(ModelMap modelMap, Principal principal) {
        List<User> userList = userService.listUsers();
        modelMap.addAttribute("usersList", userList);
        ModelAndView modelAndView = new ModelAndView("allusers");
        modelAndView.addObject("switch", currentUser.getCurrentUser(principal));
        return modelAndView;
    }
}

