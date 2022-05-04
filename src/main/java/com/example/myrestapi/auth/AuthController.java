package com.example.myrestapi.auth;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.myrestapi.user.User;
import com.example.myrestapi.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/authenticate")
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new RuntimeException("Incorrect username or password: " + e);
        }

        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        String token = JWT.create()
                .withIssuer("bar-review-api")
                .withSubject(authenticationRequest.getUsername())
                .sign(algorithm);

        return new AuthenticationResponse(token);
    }

    @PostMapping("/register")
    public String addUser(@RequestBody @Valid User user) {
        user.setId(0L); // in case client passes an id
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        User userInDb = userService.addUser(user);

        user.setId(userInDb.getId());
        return "User with name " + userInDb.getName() + " was registered.";
    }
}
