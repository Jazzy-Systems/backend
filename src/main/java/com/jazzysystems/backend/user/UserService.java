package com.jazzysystems.backend.user;

public interface UserService {
    User findUserByEmail(String email);

    Boolean existsByEmail(String email);

    User saveUser(UserDTO userDTO);
}
