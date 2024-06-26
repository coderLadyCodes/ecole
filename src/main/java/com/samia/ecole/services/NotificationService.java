package com.samia.ecole.services;

import com.samia.ecole.entities.Validation;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
                        
                        """,
                validation.getUser().getName(),
                validation.getCode());
        simpleMailMessage.setText(text);
        javaMailSender.send(simpleMailMessage);
    }
}
