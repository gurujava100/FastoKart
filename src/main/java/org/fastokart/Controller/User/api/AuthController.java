package org.fastokart.Controller.User.api;

import org.fastokart.dto.UserLoginDTO;
import org.fastokart.dto.UserRegisterDTO;
import org.fastokart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody UserRegisterDTO dto) {

        userService.register(dto);
        return "User Registered Successfully";
    }
    @PostMapping("/login")
    public String login(@RequestBody UserLoginDTO dto) {
        return userService.login(dto);
    }
}
