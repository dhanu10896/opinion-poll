package com.fullstackwebapp.opinionpoll.controller;


import com.fullstackwebapp.opinionpoll.exception.AppException;
import com.fullstackwebapp.opinionpoll.model.Role;
import com.fullstackwebapp.opinionpoll.model.RoleName;
import com.fullstackwebapp.opinionpoll.model.User;
import com.fullstackwebapp.opinionpoll.payloads.ApiResponse;
import com.fullstackwebapp.opinionpoll.payloads.LoginRequest;
import com.fullstackwebapp.opinionpoll.payloads.SignUpRequest;
import com.fullstackwebapp.opinionpoll.repository.RoleRepository;
import com.fullstackwebapp.opinionpoll.repository.UserRepository;
import com.fullstackwebapp.opinionpoll.repository.security.VerificationTokenRepository;
import com.fullstackwebapp.opinionpoll.security.event.UserRegistrationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

//    @Autowired
//    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest, WebRequest request) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false,
                    "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false,
                    "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getFullName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        String appUrl = request.getContextPath();

        try {
            applicationEventPublisher.publishEvent(new UserRegistrationEvent(user));
        } catch (Exception e) {

        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "User registered successfully"));
    }

    @GetMapping(value = "/registrationConfirm")
    public  ResponseEntity<?> confirmRegistration(final HttpServletRequest request, @RequestParam("token") final String token)
            throws UnsupportedEncodingException {
        Locale locale = request.getLocale();
        final String result = verificationTokenRepository.validateVerificationToken(token);
        if (result.equals("valid")) {
            final User user = verificationTokenRepository.findByToken(token).getUser();
            // if (user.isUsing2FA()) {
            // model.addAttribute("qr", userService.generateQRUrl(user));
            // return "redirect:/qrcode.html?lang=" + locale.getLanguage();
            // }
            authWithoutPassword(user);

        }
        return new ResponseEntity(new ApiResponse("valid".equals(result),
                result),
                HttpStatus.OK);
    }

    private void authWithoutPassword(User user) {
        Set<Role> roles = user.getRoles();
        Collection<? extends GrantedAuthority> authorities = null;
        if (roles!=null)
            authorities = user.getRoles().stream().map(role ->
                    new SimpleGrantedAuthority(role.getName().name())
            ).collect(Collectors.toList());


        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}