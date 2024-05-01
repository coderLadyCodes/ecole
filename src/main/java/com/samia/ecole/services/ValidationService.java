package com.samia.ecole.services;

import com.samia.ecole.entities.User;
import com.samia.ecole.entities.Validation;
import com.samia.ecole.exceptions.CustomException;
import com.samia.ecole.repositories.ValidationRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

import static java.time.temporal.ChronoUnit.MINUTES;
@Transactional
@Service
public class ValidationService {
    private final ValidationRepository validationRepository;
    private final NotificationService notificationService;

    public ValidationService(ValidationRepository validationRepository, NotificationService notificationService) {
        this.validationRepository = validationRepository;
        this.notificationService = notificationService;
    }
    public void enregistrer(User user){
        Validation validation =  new Validation();
        validation.setUser(user);
        Instant creation = Instant.now();
        validation.setCreation(creation);
        Instant expiration = creation.plus(10, MINUTES);
        validation.setExpiration(expiration);

        Random random = new Random();
        int randomInteger = random.nextInt(999999);
        String code = String.format("%06d", randomInteger);
        validation.setCode(code);
        this.validationRepository.save(validation);
        this.notificationService.envoyer(validation);
    }
    public Validation codeValidation (String code){
        return validationRepository.findByCode(code).orElseThrow(()->new CustomException("code not found", HttpStatus.NOT_FOUND));
    }
    @Scheduled(cron = "0 */1 * * * *")
    public void clean(){
        this.validationRepository.deleteByExpirationBefore(Instant.now());
    }
}
