package org.fastokart.mapper;

import org.fastokart.dto.UpdateProfileDTO;
import org.fastokart.dto.UserRegisterDTO;
import org.fastokart.dto.UserResponseDTO;
import org.fastokart.model.UserModel;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserMapper {
    public UserModel toEntity(UserRegisterDTO dto){
        UserModel user = new UserModel();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setPassword(dto.getPassword());
        return user;
    }
    public UserResponseDTO toDTO(UserModel user){
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setProfileImageUrl(
                user.getProfileImageUrl() != null && !user.getProfileImageUrl().isEmpty()
                        ? "/uploads/profile/" + user.getProfileImageUrl()
                        : "/images/default-user.png"
        );
        return dto;
    }
    public static void updateUserFromDTO(UpdateProfileDTO dto, UserModel user) {
        user.setName(dto.getName());
        user.setPhone(dto.getPhone());

        if (!Objects.equals(user.getEmail(), dto.getEmail())) {
            user.setEmail(dto.getEmail());
            user.setEmailVerified(false);
        }
    }
}
