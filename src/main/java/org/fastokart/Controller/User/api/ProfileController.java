package org.fastokart.Controller.User.api;

import jakarta.servlet.http.HttpSession;
import org.fastokart.dto.ProfileResponseDTO;
import org.fastokart.dto.UpdateProfileDTO;
import org.fastokart.mapper.UserMapper;
import org.fastokart.model.UserModel;
import org.fastokart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/my-account")
public class ProfileController {
    @Autowired
    private UserService userService;

    @GetMapping("/profile-page")
    public String profilePage(Model model, @RequestParam(required = false) Long userId) {
        // Default to userId = 1 for testing
        if (userId == null) userId = 1L;

        ProfileResponseDTO profile = userService.getProfile(userId);
        model.addAttribute("profile", profile);
        model.addAttribute("userId", userId); // hidden field for form
        return "user/profile"; // templates/user/profile.html
    }
    @GetMapping
    public ProfileResponseDTO viewProfile(@RequestParam Long userId) {
        Long testUserId = 1L;
        return userService.getProfile(userId);
    }
    @PostMapping("/profile/update")
    @ResponseBody
    public Map<String, Object> updateProfile(@RequestBody UpdateProfileDTO dto, HttpSession session) {


        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            Map<String, Object> error = new HashMap<>();
            error.put("status", "UNAUTHORIZED");
            error.put("message", "Please login first");
            return error;
        }

        userService.updateProfile(userId, dto);

        Map<String, Object> resp = new HashMap<>();
        resp.put("status", "PROFILE_UPDATED");
        return resp;
    }
    @GetMapping("/profile")
    public String profilePage(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) return "redirect:/login";

        UserModel user = userService.getUserById(userId);
        model.addAttribute("user", user);

        return "redirect:/profile";
    }

}
