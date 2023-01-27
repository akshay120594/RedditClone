package com.microservices.redclone.service;

import com.microservices.redclone.dto.RegisterRequest;
import com.microservices.redclone.exceptions.SpringRedditException;
import com.microservices.redclone.modal.NotificationEmail;
import com.microservices.redclone.modal.User;
import com.microservices.redclone.modal.VerificationToken;
import com.microservices.redclone.repository.UserRepository;
import com.microservices.redclone.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Data
@AllArgsConstructor
@Transactional
@Service
public class AuthService {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    /*private final AuthenticationManager authenticationManager;*/
    /*private final JWTUtility jwtProvider;*/


    public void signUp(RegisterRequest registerRequest){
        User user=new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setCreated(Instant.now());
        user.setEnabled(false);
        userRepository.save(user);
        String token=generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail("Please Activate your Account",
                user.getEmail(), "Thank you for signing up to Spring Reddit, " +
                "please click on the below url to activate your account : " +
                "http://localhost:8080/api/auth/accountVerification/" + token));
    }

    private String generateVerificationToken(User user) {
        String token= UUID.randomUUID().toString();
        VerificationToken verificationToken=new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token){
        Optional<VerificationToken> verificationToken=verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(()->new SpringRedditException("invalid token"));
        fetchUserAndEnable(verificationToken.get());
    }

    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username=verificationToken.getUser().getUsername();
        User user=userRepository.findByUsername(username).orElseThrow(()->new SpringRedditException("user not found"+username));
        user.setEnabled(true);
        userRepository.save(user);
    }



}
