package org.fastokart.service.impl;

import org.fastokart.dto.ProfileResponseDTO;
import org.fastokart.dto.UserLoginDTO;
import org.fastokart.dto.UserRegisterDTO;
import org.fastokart.mapper.ProfileMapper;
import org.fastokart.mapper.UserMapper;
import org.fastokart.model.UserModel;
import org.fastokart.repository.UserRepository;
import org.fastokart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Override
    public void register(UserRegisterDTO dto) {

        UserModel user = new UserModel();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        userRepository.save(user);
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


}
