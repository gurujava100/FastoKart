package org.fastokart.service;

import org.fastokart.dto.UserRegisterDTO;

public interface UserService {
    // register new user
    void register(UserRegisterDTO dto);
}
