package com.fullstackwebapp.opinionpoll.repository.security;

import com.fullstackwebapp.opinionpoll.model.User;
import com.fullstackwebapp.opinionpoll.model.security.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository
        extends JpaRepository<VerificationToken, Long>, VerificationTokenRepositoryCustom {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);
}
