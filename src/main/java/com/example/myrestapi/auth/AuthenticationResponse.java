package com.example.myrestapi.auth;

import lombok.Data;

@Data
public class AuthenticationResponse {
    private final String jwt;
}
