package com.example.qcassistant.web.auth;

import com.example.qcassistant.domain.dto.auth.UserDisplayDto;
import com.example.qcassistant.domain.dto.auth.UserRoleEditDto;
import com.example.qcassistant.service.auth.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String getAllUsers(Model model){
        List<UserDisplayDto> users = this.userService.getAllUsers();
        model.addAttribute("users", users);
        return "users-all";
    }

    @GetMapping("/users/{id}")
    public String getEditUserRoles(@PathVariable Long id, Model model){
        UserDisplayDto user = this.userService.displayUser(id);
        model.addAttribute("user", user);
        return "users-edit";
    }

    @PostMapping("/users/{id}")
    public String editUserRoles(@PathVariable Long id,
                                UserRoleEditDto userRoleEditDto){
        this.userService.editUserRoles(userRoleEditDto);
        return "redirect:/";
    }
}
