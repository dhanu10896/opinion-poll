package com.fullstackwebapp.opinionpoll.security.service;

import com.fullstackwebapp.opinionpoll.model.User;
import com.fullstackwebapp.opinionpoll.repository.UserRepository;
import com.fullstackwebapp.opinionpoll.repository.security.VerificationTokenRepository;
import com.fullstackwebapp.opinionpoll.security.event.UserRegistrationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class EmailVerificationListener implements ApplicationListener<UserRegistrationEvent> {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;


    @Override
    public void onApplicationEvent(UserRegistrationEvent userRegistrationEvent) {
        User user = userRegistrationEvent.getUser();
        String token = UUID.randomUUID().toString();
        verificationTokenRepository.createVerificationToken(user, token);
        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText("click here to verify" +
                ": " + " rn" + "http://localhost:5000/api/auth/confirmRegitration/"+token);
        javaMailSender.send(email);

    }
}
