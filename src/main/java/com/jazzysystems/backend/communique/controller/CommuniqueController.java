package com.jazzysystems.backend.communique.controller;

import java.util.List;
import java.util.Optional;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.thymeleaf.context.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;

import com.jazzysystems.backend.communique.Communique;
import com.jazzysystems.backend.communique.dto.CommuniqueDTO;
import com.jazzysystems.backend.communique.service.CommuniqueService;
import com.jazzysystems.backend.person.Person;
import com.jazzysystems.backend.person.repository.PersonRepository;
import com.jazzysystems.backend.resident.Resident;
import com.jazzysystems.backend.resident.repository.ResidentRepository;
import com.jazzysystems.backend.typeCommunique.TypeCommunique;
import com.jazzysystems.backend.typeCommunique.repository.TypeCommuniqueRepository;
import com.jazzysystems.backend.util.emailSender.EmailService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/communique/")
public class CommuniqueController {

    private static final String TEMPLATE_NAME = "communique";
    private static final String SPRING_LOGO_IMAGE = "templates/images/Logo.png";
    private static final String PNG_MIME = "image/png";

    final String mailFrom = "jazzysystem2022+1@gmail.com";

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine htmlTemplateEngine;
    @Autowired
    private final CommuniqueService communiqueService;
    @Autowired
    private final PersonRepository personRepository;
    @Autowired
    private final TypeCommuniqueRepository typeCommuniqueRepository;
    @Autowired
    private final ResidentRepository residentRepository;
    @Autowired
    private final EmailService emailService;
    
    @GetMapping(value = "")
    public ResponseEntity<?> findAllCommuniques() {
        return new ResponseEntity<>(communiqueService.findAll(), HttpStatus.OK);
    }

    @GetMapping({ "/{communiqueId}" })
    public ResponseEntity<?> findCommunique(@PathVariable Long communiqueId) {
        Communique communique = communiqueService.findById(communiqueId);
        return new ResponseEntity<Communique>(communique, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> save(@RequestBody CommuniqueDTO communiqueDTO) {
        String emailOrusername = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Person> optionalPerson = personRepository.findByEmail(emailOrusername);
        Optional<TypeCommunique> optionalTypeCommunique = typeCommuniqueRepository
                .findByTypeName(communiqueDTO.getTypeName());
        if (optionalPerson.isPresent() && optionalTypeCommunique.isPresent()) {
            Person person = optionalPerson.get();
            TypeCommunique typeCommunique = optionalTypeCommunique.get();
            communiqueDTO.setPerson(person);
            communiqueDTO.setTypeCommunique(typeCommunique);
            Communique communique = communiqueService.save(communiqueDTO);
            System.out.println(communique.getDescription());
            this.sendEmailtoAllResidents(communique);
            System.out.println(communique.getTitleCommunique());
            return new ResponseEntity<Communique>(communique, HttpStatus.CREATED);
        }
        return new ResponseEntity<String>("Error Bad Request, please check Person and type", HttpStatus.BAD_REQUEST);
    }

    // TODO improve implementations
    @PutMapping({ "/{communiqueId}" })
    public ResponseEntity<?> update(@PathVariable("communiqueId") Long communiqueId,
            @RequestBody CommuniqueDTO communiqueDTO) {
        String emailOrusername = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Person> optionalPerson = personRepository.findByEmail(emailOrusername);
        Optional<TypeCommunique> optionalTypeCommunique = typeCommuniqueRepository
                .findByTypeName(communiqueDTO.getTypeName());
        if (optionalPerson.isPresent() && optionalTypeCommunique.isPresent()) {
            Person person = optionalPerson.get();
            TypeCommunique typeCommunique = optionalTypeCommunique.get();
            communiqueDTO.setPerson(person);
            communiqueDTO.setTypeCommunique(typeCommunique);
            Communique communique = communiqueService.update(communiqueId, communiqueDTO);
            return new ResponseEntity<Communique>(communique, HttpStatus.CREATED);
        }
        return new ResponseEntity<String>("Bad Request please check the fields", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping({ "/{communiqueId}" })
    public ResponseEntity<?> deleteById(@PathVariable("communiqueId") Long communiqueId) {
        communiqueService.deleteById(communiqueId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void sendEmailtoAllResidents(Communique communique) {
        System.out.println("---intentando enviar mensajes----");
        List<Resident> residents = residentRepository.findAll();
        residents.forEach((resident) -> {
            this.sendEmail(resident.getPerson(), communique);
        });
    }

    private void sendEmail(Person person, Communique communique) {
        String confirmationUrl = "generated_confirmation_url";
        final String mailFromName = "JazzySystems";

        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper email;
        try {
            email = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            email.setTo(person.getEmail());
            email.setSubject(communique.getTitleCommunique());
            email.setFrom(new InternetAddress(mailFrom, mailFromName));

            final Context ctx = new Context(LocaleContextHolder.getLocale());
            ctx.setVariable("name", person.getFirstName() + " " + person.getLastName());
            ctx.setVariable("title", communique.getTitleCommunique());
            ctx.setVariable("description", communique.getDescription());
            ctx.setVariable("datePublished", communique.getDatePublished());
            ctx.setVariable("springLogo", SPRING_LOGO_IMAGE);
            ctx.setVariable("url", confirmationUrl);

            final String htmlContent = this.htmlTemplateEngine.process(TEMPLATE_NAME, ctx);

            email.setText(htmlContent, true);

            ClassPathResource clr = new ClassPathResource(SPRING_LOGO_IMAGE);

            email.addInline("springLogo", clr, PNG_MIME);

            mailSender.send(mimeMessage);
            System.out.println("El email se envio correctamente\n \n");
        } catch (Exception e) {
            System.out.println(e.getMessage() + " No se pudo enviar el mensaje");
        }
    }
}
