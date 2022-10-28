package com.jazzysystems.backend.role.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class RoleDTO {
    private Long roleId;

    @NotBlank
    private String roleName;

    @NotBlank
    private boolean isEnabled;

    
}
