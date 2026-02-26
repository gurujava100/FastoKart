package org.fastokart.service.impl;

import org.fastokart.dto.UserRegisterDTO;
import org.fastokart.mapper.UserMapper;
import org.fastokart.model.UserModel;
import org.fastokart.repository.UserRepository;
import org.fastokart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;
    @Override
    public void register(UserRegisterDTO dto) {

        if(userRepository.existsByEmail(dto.getEmail())){
            throw new RuntimeException("Email already registered");

        }

        UserModel user = userMapper.toEntity(dto);
        userRepository.save(user);
    }
}
