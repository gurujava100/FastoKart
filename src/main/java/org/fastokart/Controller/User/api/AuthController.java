package org.fastokart.Controller.User.api;

import org.fastokart.dto.UserLoginDTO;
import org.fastokart.dto.UserRegisterDTO;
import org.fastokart.model.UserModel;
import org.fastokart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

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
    public Map<String, Object> login(@RequestBody UserLoginDTO dto) {

        UserModel user = userService.login(dto);

        Map<String, Object> resp = new HashMap<>();
        resp.put("status", "LOGIN_SUCCESS");
        resp.put("userId", user.getId());

        return resp;
    }

}
