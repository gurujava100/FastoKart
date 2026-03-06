package org.fastokart.service;

import org.fastokart.dto.ProfileResponseDTO;
import org.fastokart.dto.UserLoginDTO;
import org.fastokart.dto.UserRegisterDTO;
import org.fastokart.model.UserModel;

public interface UserService {
    // register new user
    void register(UserRegisterDTO dto);
    UserModel login(UserLoginDTO dto);
    ProfileResponseDTO getProfile(Long userId);

    // Edit Profile (name, phone, email)
   // ProfileResponseDTO updateProfile(Long userId, ProfileUpdateDTO dto);

    // Upload Profile Photo
   // String uploadProfilePhoto(Long userId, MultipartFile file);

    // Verify Email (after clicking link)
   // void verifyEmail(Long userId);

    // Verify Phone (after OTP validation)
    //void verifyPhone(Long userId);
}
