package com.jazzysystems.backend.user.dto;

import com.jazzysystems.backend.person.Person;
import com.jazzysystems.backend.role.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long userId;
    private String password;
    private Boolean isEnabled;
    private Person person;
    private Role role;

}
