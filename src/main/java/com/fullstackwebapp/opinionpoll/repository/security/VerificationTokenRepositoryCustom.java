package com.fullstackwebapp.opinionpoll.repository.security;

import com.fullstackwebapp.opinionpoll.model.User;
import com.fullstackwebapp.opinionpoll.model.security.VerificationToken;
import org.springframework.data.repository.CrudRepository;

public interface VerificationTokenRepositoryCustom {
    void createVerificationToken(User user, String token) ;

    String validateVerificationToken(String token);
}
