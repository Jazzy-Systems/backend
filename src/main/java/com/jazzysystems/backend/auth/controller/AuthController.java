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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jazzysystems.backend.apartment.Apartment;
import com.jazzysystems.backend.apartment.service.ApartmentService;
import com.jazzysystems.backend.auth.dto.JwtResponsePOJO;
import com.jazzysystems.backend.auth.dto.LoginPOJO;
import com.jazzysystems.backend.auth.dto.RecoverPasswordDTO;
import com.jazzysystems.backend.auth.dto.RegisterUserPOJO;
import com.jazzysystems.backend.auth.dto.SignUpDTO;
import com.jazzysystems.backend.auth.jwt.JwtUtils;
import com.jazzysystems.backend.company.Company;
import com.jazzysystems.backend.company.service.CompanyService;
import com.jazzysystems.backend.person.Person;
import com.jazzysystems.backend.person.service.PersonService;
import com.jazzysystems.backend.resident.Resident;
import com.jazzysystems.backend.resident.dto.ResidentDTO;
import com.jazzysystems.backend.resident.service.ResidentService;
import com.jazzysystems.backend.role.Role;
import com.jazzysystems.backend.role.service.RoleService;
import com.jazzysystems.backend.securityguard.SecurityGuard;
import com.jazzysystems.backend.securityguard.dto.SecurityGuardDTO;
import com.jazzysystems.backend.securityguard.service.SecurityGuardService;
import com.jazzysystems.backend.user.User;
import com.jazzysystems.backend.user.UserDetailsImpl;
import com.jazzysystems.backend.user.dto.UserDTO;
import com.jazzysystems.backend.user.repository.UserRepository;
import com.jazzysystems.backend.user.service.UserService;
import com.jazzysystems.backend.util.SecurityCodeGenerator;
import com.jazzysystems.backend.util.emailSender.EmailService;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@AllArgsConstructor
public class AuthController {

    private static final String TEMPLATE_NAME = "emailCode";

    @Autowired
    private EmailService emailService;
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
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private com.jazzysystems.backend.auth.Authentication authentication;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SecurityCodeGenerator securityCodeGenerator;

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
                roles, userDetails.isEnabled()));
    }

    @Transactional(rollbackOn = Exception.class)
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpDTO signUpDTO) {

        // Find or save Person
        Person person = null;
        if (userService.existsByEmail(signUpDTO.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }
        person = personService.findPersonByEmail(signUpDTO.getEmail());

        Boolean saveUser = false;
        // find rol
        Role role = roleService.findbyRoleName(signUpDTO.getRoleName());
        if (role.getRoleName().equals("ROLE_RESIDENT")) {
            // find apartment
            Apartment apartment = apartmentService.findByCodeApartment(
                    signUpDTO.getCode());
            // find resident
            Resident resident = residentService.findByPerson(person);
            // check if apartment and resident are correct
            saveUser = (apartment.getApartmentId().equals(resident.getApartment().getApartmentId()));
        } else if (role.getRoleName().equals("ROLE_GUARD")) {
            // find company
            Company company = companyService.finbByCodeCompany(
                    signUpDTO.getCode());
            // find securityGuard
            SecurityGuard securityGuard = securityGuardService.findByPerson(person);
            // check if company and securityguard are correct
            saveUser = (company.getCompanyId().equals(securityGuard.getCompany().getCompanyId()));
        } else if (role.getRoleName().equals("ROLE_ADMIN")) {
            saveUser = true;
        }

        // Create new user's account only if the data is correct
        if (saveUser) {
            UserDTO userDTO = new UserDTO(null, passwordEncoder.encode(signUpDTO.getPassword()),
                    true, person, role);
            userService.saveUser(userDTO);
            return ResponseEntity.ok(("User registered successfully!"));
        }
        return ResponseEntity
                .badRequest()
                .body("Error: Por favor verifique los campos");

    }

    @Transactional(rollbackOn = Exception.class)
    @PostMapping("/registerPerson")
    public ResponseEntity<?> registerPerson(@Valid @RequestBody RegisterUserPOJO registerUserPOJO) {

        Person person = null;
        if (userService.existsByEmail(registerUserPOJO.getPersonDTO().getEmail()) ||
                personService.existsByDni(registerUserPOJO.getPersonDTO().getDni())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }
        Role role = roleService.findbyRoleName(registerUserPOJO.getRoleName());
        // SAVE person
        person = personService.savePerson(registerUserPOJO.getPersonDTO());
        if (role.getRoleName().equals("ROLE_RESIDENT")) {
            Apartment apartment = apartmentService.findByBuildingNameAndNumber(
                    registerUserPOJO.getApartmentDTO().getBuildingName(),
                    registerUserPOJO.getApartmentDTO().getApartmentNumber());

            Boolean isRepresentative = !residentService.ExistsByApartmentAndIsRepresentative(true, apartment);

            ResidentDTO residentDTO = new ResidentDTO(person, apartment, isRepresentative, true);
            residentService.saveResident(residentDTO);
            emailService.sendRegisterCode(person, apartment.getCodeApartment(), TEMPLATE_NAME);
        } else if (role.getRoleName().equals("ROLE_GUARD")) {
            Company company = companyService.findByCompanyName(
                    registerUserPOJO.getCompanyName());
            SecurityGuardDTO securityGuardDTO = new SecurityGuardDTO(person, company);
            securityGuardService.saveSecurityGuard(securityGuardDTO);
            emailService.sendRegisterCode(person, company.getCodeCompany(), TEMPLATE_NAME);
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("Error: The Role doesn'not exist");
        }

        return ResponseEntity.ok(("Person registered successfully!"));
    }

    @PostMapping(value = "/recover")
    public ResponseEntity<?> sendRecoverPassword(@RequestBody RecoverPasswordDTO recoverPasswordDTO) {
        int LEN = 10;
        User user = userService.findUserByEmail(recoverPasswordDTO.getEmail());
        String password = securityCodeGenerator.generatePassword(LEN);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(false);
        userRepository.save(user);
        emailService.sendRecoveredPassword(user.getPerson(), password);
        return ResponseEntity.ok(("A su correo se ha enviado los pasos para recuperar contraseña"));
    }

    @PutMapping(value = "/recover")
    public ResponseEntity<?> recoverPassword(@RequestBody RecoverPasswordDTO recoverPasswordDTO) {
        // TODO changedpassword email
        User user = userService.findUserByEmail(recoverPasswordDTO.getEmail());
        System.out.println(user.getUserId());
        if (passwordEncoder.matches(
                recoverPasswordDTO.getCurrentPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(recoverPasswordDTO.getNewPassword()));
            userRepository.save(user);
            emailService.sendChangePasswordNotification(user.getPerson());
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Datos incorrectos");
        }
        return ResponseEntity.ok(("Se ha cambiado la contraseña!"));
    }

    @PutMapping(value = "/changepassword")
    public ResponseEntity<?> changePassword(@RequestBody RecoverPasswordDTO recoverPasswordDTO) {

        User user = userService.findUserByEmail(recoverPasswordDTO.getEmail());
        System.out.println(user.getUserId());
        if (user.equals(authentication.getUser()) &&
                passwordEncoder.matches(
                        recoverPasswordDTO.getCurrentPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(recoverPasswordDTO.getNewPassword()));
            userRepository.save(user);
            emailService.sendChangePasswordNotification(user.getPerson());
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Datos incorrectos");
        }
        return ResponseEntity.ok(("Se ha cambiado la contraseña!"));
    }

}
