package com.microservices.redclone.controller;

import com.microservices.redclone.dto.AuthenticationResponse;
import com.microservices.redclone.dto.LoginRequest;
import com.microservices.redclone.dto.RegisterRequest;
import com.microservices.redclone.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
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


}
