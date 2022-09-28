package com.jazzysystems.backend.role.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jazzysystems.backend.role.Role;
import com.jazzysystems.backend.role.dto.RoleDTO;
import com.jazzysystems.backend.role.service.RoleService;

import lombok.RequiredArgsConstructor;

//TODO preautorized(admin for all methods? or could the resident have acces to
//few of them)
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/role")
public class RoleController {
    private final RoleService roleService;

    @GetMapping(value = "")
    public ResponseEntity<?> findAllRole() {
        return new ResponseEntity<>(roleService.findAll(), HttpStatus.OK);
    }

    @GetMapping({ "/{roleId}" })
    public ResponseEntity<?> findRole(@PathVariable Long roleId) {
        Role role = roleService.findRoleById(roleId);
        return new ResponseEntity<Role>(role, HttpStatus.OK);
    }
    
    /*
    @GetMapping({"/{roleName}" })
    public ResponseEntity<?> findbyRoleName(@PathVariable String roleName) {
        Role role = roleService.findbyRoleName(roleName);
        return new ResponseEntity<Role>(role, HttpStatus.OK);
    }
    */
    
    @PostMapping()
    public ResponseEntity<?> saveRole(@RequestBody RoleDTO roleDTO) {
        Role role = roleService.saveRole(roleDTO);
        return new ResponseEntity<Role>(role, HttpStatus.CREATED);
    }

    @PutMapping({ "/{roleId}" })
    public ResponseEntity<?> updateRole(@PathVariable("roleId") Long roleId,
            @RequestBody RoleDTO roleDTO) {
        Role role = roleService.updateRole(roleId, roleDTO);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @DeleteMapping({ "/{roleId}" })
    public ResponseEntity<?> deleteRoleById(@PathVariable("roleId") Long roleId) {
        roleService.deleteRoleById(roleId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
