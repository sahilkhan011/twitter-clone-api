package com.ssk.twitter_api.rest;

import com.ssk.twitter_api.model.User;
import com.ssk.twitter_api.service.JwtService;
import com.ssk.twitter_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterLogin {

    private final UserService userService;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    @Autowired
    public RegisterLogin(UserService userService, AuthenticationManager authManager, JwtService jwtService) {
        this.userService = userService;
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    @PostMapping("register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try {
            // Attempt authentication
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            if (auth.isAuthenticated()) {
                // Generate and return JWT token
                return new ResponseEntity<>(jwtService.generateToken(user.getUsername()), HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Failed");
            }
        } catch (BadCredentialsException e) {
            // Handle invalid credentials
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
}
