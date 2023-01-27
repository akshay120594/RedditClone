package com.microservices.redclone.controller;

import com.microservices.redclone.dto.AuthenticationResponse;
import com.microservices.redclone.dto.JwtResponse;
import com.microservices.redclone.dto.LoginRequest;
import com.microservices.redclone.dto.RegisterRequest;
import com.microservices.redclone.security.JwtProvider;
import com.microservices.redclone.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    @PostMapping(value = "/signup")
    public ResponseEntity<String> signUp(@RequestBody RegisterRequest registerRequest){
        authService.signUp(registerRequest);
        return new ResponseEntity<>("user registration successful", HttpStatus.OK);
    }
    @GetMapping(value = "/accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token){
        authService.verifyAccount(token);
        return new ResponseEntity<>("account activated successfully", HttpStatus.OK);

    }
        @PostMapping("/login")
        public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generateJwtToken(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(),userDetails.getAuthorities()));
            }
        }





