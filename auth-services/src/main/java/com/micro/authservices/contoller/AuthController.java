package com.micro.authservices.contoller;


import com.micro.authservices.dto.AuthRequest;
import com.micro.authservices.dto.UserCredentialDto;
import com.micro.authservices.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String addNewUser(@RequestBody UserCredentialDto user) {
        return service.saveUser(user.convertUserCredentialDtoToUserCredential());
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            if (authenticate.isAuthenticated()) {
                return service.generateToken(authRequest.getUsername());
            }
        } catch (AuthenticationException e) {

            throw new BadCredentialsException("Invalid username or password");
        }

        throw new BadCredentialsException("Invalid access");
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        service.validateToken(token);
        return "Token is valid";
    }
}

