package org.fastokart.mapper;

import org.fastokart.dto.ProfileResponseDTO;
import org.fastokart.dto.UpdateProfileDTO;
import org.fastokart.model.UserModel;

import java.util.Objects;

public class ProfileMapper {
    public static ProfileResponseDTO toDTO(UserModel user) {
        return ProfileResponseDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .profileImageUrl(
                        user.getProfileImageUrl() != null
                                ? "/uploads/profile/" + user.getProfileImageUrl()
                                : "/images/default-user.png"
                )
                .emailVerified(user.isEmailVerified())
                .phoneVerified(user.isPhoneVerified())
                .build();
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
