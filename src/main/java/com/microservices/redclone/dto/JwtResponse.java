package com.microservices.redclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Data
public class JwtResponse {
    private final String jwtToken;
    private final String type = "Bearer";
    private final String username;
    private final Collection<? extends GrantedAuthority> authorities;

    public JwtResponse(String jwtToken, String username, Collection<? extends GrantedAuthority> authorities) {
        this.jwtToken = jwtToken;
        this.username = username;
        this.authorities = authorities;
    }

    public String getToken() {
        return this.jwtToken;
    }
    public String getTokenType() {
        return type;
    }
    public String getUsername() {
        return this.username;
    }
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}

