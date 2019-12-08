package com.fullstackwebapp.opinionpoll.security.event;

import com.fullstackwebapp.opinionpoll.model.User;
import org.springframework.context.ApplicationEvent;

public class UserRegistrationEvent extends ApplicationEvent {

    private static final long searialVersionUID = -4242424248324827498L;
    private final User user;
    public UserRegistrationEvent(User user) {
        super(user);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
