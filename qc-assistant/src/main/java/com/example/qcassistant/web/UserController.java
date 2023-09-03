package com.example.qcassistant.web;

import com.example.qcassistant.domain.dto.UserDisplayDto;
import com.example.qcassistant.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
