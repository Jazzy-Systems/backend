package com.jazzysystems.backend.auth.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jazzysystems.backend.apartment.Apartment;
import com.jazzysystems.backend.apartment.service.ApartmentService;
import com.jazzysystems.backend.auth.dto.JwtResponsePOJO;
import com.jazzysystems.backend.auth.dto.LoginPOJO;
import com.jazzysystems.backend.auth.dto.RegisterUserPOJO;
import com.jazzysystems.backend.auth.jwt.JwtUtils;
import com.jazzysystems.backend.company.Company;
import com.jazzysystems.backend.company.service.CompanyService;
import com.jazzysystems.backend.person.Person;
import com.jazzysystems.backend.person.service.PersonService;
import com.jazzysystems.backend.resident.dto.ResidentDTO;
import com.jazzysystems.backend.resident.service.ResidentService;
import com.jazzysystems.backend.role.Role;
import com.jazzysystems.backend.role.service.RoleService;
import com.jazzysystems.backend.securityguard.dto.SecurityGuardDTO;
import com.jazzysystems.backend.securityguard.service.SecurityGuardService;
import com.jazzysystems.backend.user.UserDetailsImpl;
import com.jazzysystems.backend.user.dto.UserDTO;
import com.jazzysystems.backend.user.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PersonService personService;

    @Autowired
    private ResidentService residentService;

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private SecurityGuardService securityGuardService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(
            @Valid @RequestBody LoginPOJO loginPOJO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginPOJO.getEmail(), loginPOJO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponsePOJO(jwt,
                userDetails.getUsername(),
                roles));
    }

    @Transactional(rollbackOn = Exception.class)
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody RegisterUserPOJO registerUserPOJO) {

        // Find or save Person
        Person person = null;
        if (userService.existsByEmail(registerUserPOJO.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }
        if (registerUserPOJO.getRoleName().equals("ROLE_ADMIN")
                && !personService.existsByEmail(registerUserPOJO.getEmail())) {
            person = personService.savePerson(registerUserPOJO.getPersonDTO());

        } else {
            person = personService.findPersonByEmail(registerUserPOJO.getEmail());
        }

        // find rol
        Role role = roleService.findbyRoleName(registerUserPOJO.getRoleName());
        if (role.getRoleName().equals("ROLE_RESIDENT")) {
            // find apartment
            Apartment apartment = apartmentService.findByCodeApartment(
                    registerUserPOJO.getCodeApartment());

            ResidentDTO residentDTO = new ResidentDTO(person, apartment, true, true);

            residentService.saveResident(residentDTO);

        } else if (role.getRoleName().equals("ROLE_GUARD")) {
            // find apartment
            Company company = companyService.findByCompanyName(
                    registerUserPOJO.getCompanyName());
            SecurityGuardDTO securityGuardDTO = new SecurityGuardDTO(person, company);
            securityGuardService.saveSecurityGuard(securityGuardDTO);
        } else {

        }

        // Create new user's account
        UserDTO userDTO = new UserDTO(null, passwordEncoder.encode(registerUserPOJO.getPassword()),
                false, person, role);

        userService.saveUser(userDTO);

        return ResponseEntity.ok(("User registered successfully!"));
    }

    @Transactional(rollbackOn = Exception.class)
    @PostMapping("/registerPerson")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUserPOJO registerUserPOJO) {

        Person person = null;
        if (userService.existsByEmail(registerUserPOJO.getEmail()) ||
                personService.existsByDni(registerUserPOJO.getPersonDTO().getDni())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        if (registerUserPOJO.getRoleName().equals("ROLE_ADMIN")
                && !personService.existsByEmail(registerUserPOJO.getEmail())) {
            registerUserPOJO.getPersonDTO().setEmail(registerUserPOJO.getEmail());
            person = personService.savePerson(registerUserPOJO.getPersonDTO());

        }

        Role role = roleService.findbyRoleName(registerUserPOJO.getRoleName());
        if (role.getRoleName().equals("ROLE_RESIDENT")) {
            Apartment apartment = apartmentService.findByBuildingNameAndNumber(
                    registerUserPOJO.getApartmentDTO().getBuildingName(),
                    registerUserPOJO.getApartmentDTO().getApartmentNumber());

            ResidentDTO residentDTO = new ResidentDTO(person, apartment, true, true);

            residentService.saveResident(residentDTO);

        } else if (role.getRoleName().equals("ROLE_GUARD")) {
            Company company = companyService.findByCompanyName(
                    registerUserPOJO.getCompanyName());
            SecurityGuardDTO securityGuardDTO = new SecurityGuardDTO(person, company);
            securityGuardService.saveSecurityGuard(securityGuardDTO);
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("Error: The Role doesn'not exist");
        }

        return ResponseEntity.ok(("Person registered successfully!"));
    }

}
