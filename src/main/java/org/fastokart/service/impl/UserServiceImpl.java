package org.fastokart.service.impl;

import jakarta.transaction.Transactional;
import org.fastokart.dto.*;
import org.fastokart.mapper.ProfileMapper;
import org.fastokart.mapper.UserMapper;
import org.fastokart.model.UserModel;
import org.fastokart.repository.UserRepository;
import org.fastokart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Override
    public UserModel register(UserRegisterDTO dto, MultipartFile image) {

        UserModel user = new UserModel();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        // 🔥 IMAGE UPLOAD
        if (image != null && !image.isEmpty()) {

            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();

            String uploadDir = System.getProperty("user.dir") + "/uploads/profile/";

            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            try {
                File destination = new File(dir, fileName);
                image.transferTo(destination);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Image upload failed: " + e.getMessage());
            }

            user.setProfileImageUrl(fileName);
        }

      return  userRepository.save(user);

    }

    public UserModel login(UserLoginDTO dto) {
        UserModel user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return user; // <-- return UserModel
    }

    public ProfileResponseDTO getProfile(Long userId) {

        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ProfileMapper.toDTO(user);
    }

   @Override
   @Transactional
        // 3️⃣ Update email safely
        public UserResponseDTO updateProfile(Long userId,UpdateProfileDTO dto) {
       UserModel user = userRepository.findById(userId)
               .orElseThrow(() -> new RuntimeException("User not found"));

       // Update fields
       user.setName(dto.getName());
       user.setPhone(dto.getPhone());

       String newEmail = dto.getEmail() != null ? dto.getEmail().trim() : null;
       String currentEmail = user.getEmail() != null ? user.getEmail().trim() : null;

       if (newEmail != null && !newEmail.isEmpty()) {

           if (!newEmail.equalsIgnoreCase(currentEmail)) {

               if (userRepository.existsByEmail(newEmail)) {
                   throw new RuntimeException("Email already in use");
               }

               user.setEmail(newEmail);
           }
       }
            // 4️⃣ Update profile image if provided


            // 5️⃣ Save user
            userRepository.save(user);

            // 6️⃣ Map to response DTO
            UserResponseDTO response = new UserResponseDTO();
            response.setId(user.getId());
            response.setName(user.getName());
            response.setEmail(user.getEmail()); // guaranteed not null
            response.setPhone(user.getPhone());
            // Convert image bytes to URL (for frontend)


            return response;
        }
    @Override
    public UserModel getUserById(Long id) {
        // Fetch user from DB, throw exception if not found
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
}
