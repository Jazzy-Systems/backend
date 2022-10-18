package com.jazzysystems.backend.auth.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.thymeleaf.context.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
import org.thymeleaf.TemplateEngine;

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
import com.jazzysystems.backend.resident.Resident;
import com.jazzysystems.backend.resident.dto.ResidentDTO;
import com.jazzysystems.backend.resident.service.ResidentService;
import com.jazzysystems.backend.role.Role;
import com.jazzysystems.backend.role.service.RoleService;
import com.jazzysystems.backend.securityguard.SecurityGuard;
import com.jazzysystems.backend.securityguard.dto.SecurityGuardDTO;
import com.jazzysystems.backend.securityguard.service.SecurityGuardService;
import com.jazzysystems.backend.user.UserDetailsImpl;
import com.jazzysystems.backend.user.dto.UserDTO;
import com.jazzysystems.backend.user.service.UserService;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@AllArgsConstructor
public class AuthController {

    private static final String TEMPLATE_NAME = "emailCode";
    private static final String SPRING_LOGO_IMAGE = "templates/images/Logo.png";
    private static final String PNG_MIME = "image/png";
    private static final String MAIL_SUBJECT = "Completar Registro en JazzySystemsApp";

    // @Value("${spring.mail.properties.mail.smtp.from}")
    final String mailFrom = "jazzysystem2022+1@gmail.com";

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine htmlTemplateEngine;
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
        Boolean saveUser = false;
        Role role = roleService.findbyRoleName(registerUserPOJO.getRoleName());
        if (role.getRoleName().equals("ROLE_RESIDENT")) {
            // find apartment
            Apartment apartment = apartmentService.findByCodeApartment(
                    registerUserPOJO.getCodeApartment());
            // find resident
            Resident resident = residentService.findByPerson(person);
            // check if apartment and resident are correct
            saveUser = (apartment.getApartmentId().equals(resident.getApartment().getApartmentId()));
        } else if (role.getRoleName().equals("ROLE_GUARD")) {
            // find company
            Company company = companyService.findByCompanyName(
                    registerUserPOJO.getCompanyName());
            // find securityGuard
            SecurityGuard securityGuard = securityGuardService.findByPerson(person);
            // check if company and securityguard are correct
            saveUser = (company.getCompanyId().equals(securityGuard.getCompany().getCompanyId()));
        } else {

        }

        // Create new user's account only if the data is correct
        if (saveUser) {
            UserDTO userDTO = new UserDTO(null, passwordEncoder.encode(registerUserPOJO.getPassword()),
                    false, person, role);
            userService.saveUser(userDTO);
            return ResponseEntity.ok(("User registered successfully!"));
        }
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
        Role role = roleService.findbyRoleName(registerUserPOJO.getRoleName());
        // SAVE person
        registerUserPOJO.getPersonDTO().setEmail(registerUserPOJO.getEmail());
        person = personService.savePerson(registerUserPOJO.getPersonDTO());
        // admin
        if (registerUserPOJO.getRoleName().equals("ROLE_ADMIN")
                && !personService.existsByEmail(registerUserPOJO.getEmail())) {
            registerUserPOJO.getPersonDTO().setEmail(registerUserPOJO.getEmail());
            person = personService.savePerson(registerUserPOJO.getPersonDTO());
            UserDTO userDTO = new UserDTO(null, passwordEncoder.encode(registerUserPOJO.getPassword()),
                    false, person, role);

            userService.saveUser(userDTO);

        }

        if (role.getRoleName().equals("ROLE_RESIDENT")) {
            Apartment apartment = apartmentService.findByBuildingNameAndNumber(
                    registerUserPOJO.getApartmentDTO().getBuildingName(),
                    registerUserPOJO.getApartmentDTO().getApartmentNumber());

            ResidentDTO residentDTO = new ResidentDTO(person, apartment, true, true);

            residentService.saveResident(residentDTO);
            this.sendEmail(person, apartment.getCodeApartment());

        } else if (role.getRoleName().equals("ROLE_GUARD")) {
            Company company = companyService.findByCompanyName(
                    registerUserPOJO.getCompanyName());
            SecurityGuardDTO securityGuardDTO = new SecurityGuardDTO(person, company);
            securityGuardService.saveSecurityGuard(securityGuardDTO);
            this.sendEmail(person, company.getCodeCompany());
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("Error: The Role doesn'not exist");
        }

        return ResponseEntity.ok(("Person registered successfully!"));
    }

    private void sendEmail(Person person, String code) {
        String confirmationUrl = "generated_confirmation_url";

        final String mailFromName = "JazzySystems";

        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper email;
        try {
            email = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            email.setTo(person.getEmail());
            email.setSubject(MAIL_SUBJECT);
            email.setFrom(new InternetAddress(mailFrom, mailFromName));

            final Context ctx = new Context(LocaleContextHolder.getLocale());
            ctx.setVariable("email", person.getEmail());
            ctx.setVariable("name", person.getFirstName() + " " + person.getLastName());
            ctx.setVariable("code", code);
            ctx.setVariable("springLogo", SPRING_LOGO_IMAGE);
            ctx.setVariable("url", confirmationUrl);

            final String htmlContent = this.htmlTemplateEngine.process(TEMPLATE_NAME, ctx);

            email.setText(htmlContent, true);

            ClassPathResource clr = new ClassPathResource(SPRING_LOGO_IMAGE);

            email.addInline("springLogo", clr, PNG_MIME);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            System.out.println("No se pudo enviar el mensaje");
        }

    }
}
