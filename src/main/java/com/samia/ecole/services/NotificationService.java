package com.samia.ecole.services;

import com.samia.ecole.entities.Classroom;
import com.samia.ecole.entities.User;
import com.samia.ecole.entities.Validation;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    final JavaMailSender javaMailSender;

    public NotificationService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void envoyer(Validation validation){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("notreplay@gmail.fr");
        simpleMailMessage.setTo(validation.getUser().getEmail());
        simpleMailMessage.setSubject("Votre Code d'activation");

        String text = String.format("""
                        Bonjour %s,
                        
                        Votre code d'activation pour le site de l'école est :  %s
                        
                        Ce code est valable pendant 10 minutes.
                        
                        Retournez sur le site de l'école, et suivez les instructions.
                        
                        PS: Ceci est un email automatique, ne répondez jamais à cet email.
                        
                        """,
                validation.getUser().getName(),
                validation.getCode());
        simpleMailMessage.setText(text);
        javaMailSender.send(simpleMailMessage);
    }
    public void envoyerCode(Classroom classroom){
        User userContext = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("notreplay@gmail.fr");
        simpleMailMessage.setTo(userContext.getUsername());
        simpleMailMessage.setSubject(" Le code d'accés à la classe");
        String text = String.format("""
                Bonjour %s,
                
                Le code d'accés à la classe : %s, de Maitresse / Maitre : %s  est : %s
                
                PS: Ceci est un email automatique, ne répondez jamais à cet email.
                """,
                userContext.getUsername(),
                classroom.getGrade(),
                userContext.getName(),
                classroom.getClassroomCode());
        simpleMailMessage.setText(text);
        javaMailSender.send(simpleMailMessage);
    }
}
