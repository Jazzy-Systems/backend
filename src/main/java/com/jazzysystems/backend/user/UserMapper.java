package com.jazzysystems.backend.user;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jazzysystems.backend.user.dto.UserDTO;

@Component
public class UserMapper {

    public UserDTO convertUserToDTO(User user) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, UserDTO.class);
    }

    public User convertDTOtoUser(UserDTO userDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userDTO, User.class);
    }
}
