package org.fastokart.Controller.User.api;

import jakarta.servlet.http.HttpSession;
import org.fastokart.dto.UserLoginDTO;
import org.fastokart.dto.UserRegisterDTO;
import org.fastokart.model.UserModel;
import org.fastokart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;
import org.springframework.ui.Model;
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(  @ModelAttribute UserRegisterDTO dto,
                             @RequestParam(value = "image", required = false) MultipartFile image,
                             HttpSession session,
                             Model model) {   // ✅ Inject HttpSession

        // Save the user
        UserModel savedUser = userService.register(dto, image);

        // ✅ Set session after registration
        session.setAttribute("user", savedUser);
        return "User Registered Successfully";
    }
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody UserLoginDTO dto,
                                     HttpSession session) {


        UserModel user = userService.login(dto);

        Map<String, Object> resp = new HashMap<>();

        if (user == null) {
            resp.put("status", "LOGIN_FAILED");
            resp.put("message", "Invalid email or password");
            return resp;
        }

        // ✅ FIXED KEY
        session.setAttribute("USER_ID", user.getId());

        resp.put("status", "LOGIN_SUCCESS");
        resp.put("userId", user.getId());


        return resp;
    }

}
