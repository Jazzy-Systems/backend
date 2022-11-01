package com.jazzysystems.backend.user.service;

import com.jazzysystems.backend.auth.dto.RecoverPasswordDTO;
import com.jazzysystems.backend.user.User;
import com.jazzysystems.backend.user.dto.UserDTO;

public interface UserService {
    User findUserByEmail(String email);

    Boolean existsByEmail(String email);

    User saveUser(UserDTO userDTO);

    void recoverPassword(RecoverPasswordDTO recoverPasswordDTO);

    void changePassword(RecoverPasswordDTO recoverPasswordDTO);

}
