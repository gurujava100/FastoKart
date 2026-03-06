package org.fastokart.Controller.User.api;

import org.fastokart.dto.ProfileResponseDTO;
import org.fastokart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
