package com.example.demo.model.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TokenInfo {
    private String userId;
    private String token;
    private Date issuedAt;
    private Date expiresAt;

}
