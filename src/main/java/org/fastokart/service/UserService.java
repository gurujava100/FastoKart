package org.fastokart.service;

import org.fastokart.dto.*;
import org.fastokart.model.UserModel;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    // register new user
    UserModel register(UserRegisterDTO dto, MultipartFile image);
    UserModel login(UserLoginDTO dto);
    ProfileResponseDTO getProfile(Long userId);
    UserResponseDTO updateProfile(Long userId,UpdateProfileDTO dto);
    UserModel getUserById(Long id);
    // Edit Profile (name, phone, email)
   // ProfileResponseDTO updateProfile(Long userId, ProfileUpdateDTO dto);

    // Upload Profile Photo
   // String uploadProfilePhoto(Long userId, MultipartFile file);

    // Verify Email (after clicking link)
   // void verifyEmail(Long userId);

    // Verify Phone (after OTP validation)
    //void verifyPhone(Long userId);
}
