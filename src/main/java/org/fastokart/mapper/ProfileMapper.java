package org.fastokart.mapper;

import org.fastokart.dto.ProfileResponseDTO;
import org.fastokart.model.UserModel;

public class ProfileMapper {
    public static ProfileResponseDTO toDTO(UserModel user) {
        return ProfileResponseDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .profileImageUrl(user.getProfileImageUrl())
                .emailVerified(user.isEmailVerified())
                .phoneVerified(user.isPhoneVerified())
                .build();
    }
}
