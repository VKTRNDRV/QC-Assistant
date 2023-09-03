package com.example.qcassistant.web;

import com.example.qcassistant.domain.dto.UserRegistrationDto;
import com.example.qcassistant.exception.ExistingUsernameException;
import com.example.qcassistant.exception.UnconfirmedPasswordException;
import com.example.qcassistant.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    private UserService userService;
    private SecurityContextRepository securityContextRepository;


    public RegisterController(UserService userService, SecurityContextRepository securityContextRepository) {
        this.userService = userService;
        this.securityContextRepository = securityContextRepository;
    }

    @GetMapping("/register")
    public String login(){
        return "register";
    }

    @PostMapping("/register")
    public String registerNewUser(
            UserRegistrationDto registrationDTO,
            HttpServletRequest request,
            HttpServletResponse response) {

        userService.registerUser(registrationDTO, successfulAuth -> {
            SecurityContextHolderStrategy strategy = SecurityContextHolder
                    .getContextHolderStrategy();

            SecurityContext context = strategy.createEmptyContext();
            context.setAuthentication(successfulAuth);
            strategy.setContext(context);
            securityContextRepository.saveContext(context, request, response);
        });

        return "redirect:/";
    }

    @ExceptionHandler(UnconfirmedPasswordException.class)
    public String handleUnconfirmedPassword(UnconfirmedPasswordException exc,
                                                     HttpServletRequest httpServletRequest,
                                                     Model model) {
        model.addAttribute("username", httpServletRequest.getParameter("username"));
        model.addAttribute("unconfirmed_password", true);
        return "register";
    }

    @ExceptionHandler(ExistingUsernameException.class)
    public String handleExistingUsername(ExistingUsernameException exc,
                                                     HttpServletRequest httpServletRequest,
                                                     Model model) {
        model.addAttribute("username", httpServletRequest.getParameter("username"));
        model.addAttribute("existing_username", true);
        return "register";
    }
}
