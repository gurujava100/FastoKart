package org.fastokart.mapper;

import org.fastokart.dto.UserRegisterDTO;
import org.fastokart.dto.UserResponseDTO;
import org.fastokart.model.UserModel;
import org.springframework.stereotype.Component;

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
        return dto;
    }
}
