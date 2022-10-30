package com.jazzysystems.backend.util.emailSender;

import java.util.List;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import com.jazzysystems.backend.communique.Communique;
import com.jazzysystems.backend.person.Person;
import com.jazzysystems.backend.resident.Resident;
import com.jazzysystems.backend.resident.repository.ResidentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {
    private static final String SPRING_LOGO_IMAGE = "templates/images/Logo.png";
    private static final String PNG_MIME = "image/png";
    private final String mailFromName = "JazzySystems";

    private final String mailFrom = "jazzysystem2022+1@gmail.com";

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine htmlTemplateEngine;

    @Autowired
    private final ResidentRepository residentRepository;

    private void sendEmail(String htmlContent, String to, String subject) {
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper email;
        try {
            email = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            email.setTo(to);
            email.setSubject(subject);
            email.setFrom(new InternetAddress(mailFrom, mailFromName));
            email.setText(htmlContent, true);
            ClassPathResource clr = new ClassPathResource(SPRING_LOGO_IMAGE);
            email.addInline("springLogo", clr, PNG_MIME);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            System.out.println(e.getMessage() + " No se pudo enviar el mensaje to" + to);
        }
        System.out.println("El email se envio correctamente\n \n");
    }

    public void sendCommuniqueEmailtoAll(Communique communique, String TEMPLATE_NAME) {
        List<Resident> residents = residentRepository.findAll();
        residents.forEach((resident) -> {
            this.sendCommunique(resident.getPerson(), communique, TEMPLATE_NAME);
        });
    }

    /**
     * Send Email with: TEMPLATE_NAME to person with the Communique's content
     *
     * @param person
     * @param communique
     * @param TEMPLATE_NAME
     */
    public void sendCommunique(Person person, Communique communique, String TEMPLATE_NAME) {

        try {
            final Context ctx = new Context(LocaleContextHolder.getLocale());
            ctx.setVariable("name", person.getFirstName() + " " + person.getLastName());
            ctx.setVariable("title", communique.getTitleCommunique());
            ctx.setVariable("description", communique.getDescription());
            ctx.setVariable("datePublished", communique.getDatePublished());
            ctx.setVariable("springLogo", SPRING_LOGO_IMAGE);
            // TODO url of communiques
            ctx.setVariable("url", "TODO");
            final String htmlContent = this.htmlTemplateEngine.process(TEMPLATE_NAME, ctx);
            // send email
            this.sendEmail(htmlContent, person.getEmail(), communique.getTitleCommunique());
        } catch (Exception e) {
            System.out.println(e.getMessage() + " Error en las variables del ctx");
        }

    }

    public void sendRegisterCode(Person person, String code, String TEMPLATE_NAME) {
        try {
            final Context ctx = new Context(LocaleContextHolder.getLocale());
            ctx.setVariable("email", person.getEmail());
            ctx.setVariable("name", person.getFirstName() + " " + person.getLastName());
            ctx.setVariable("code", code);
            ctx.setVariable("springLogo", SPRING_LOGO_IMAGE);
            // TODO url of signup when deployed
            ctx.setVariable("url", "TODO");
            final String htmlContent = this.htmlTemplateEngine.process(TEMPLATE_NAME, ctx);
            this.sendEmail(htmlContent, person.getEmail(), "Bienvenido a JazzySystems");
        } catch (Exception e) {
            System.out.println(e.getMessage() + " No se pudo enviar el mensaje");
        }
    }
}
