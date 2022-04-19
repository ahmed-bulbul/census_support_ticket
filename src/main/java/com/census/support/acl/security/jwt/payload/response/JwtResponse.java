package com.census.support.acl.security.jwt.payload.response;


import com.census.support.acl.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private boolean status;
    private User user;
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private long expiresAt;

    public JwtResponse(boolean status, User user, String token, String bearer, long timeout) {
        this.status = status;
        this.user = user;
        this.accessToken = token;
        this.tokenType = bearer;
        this.expiresAt = timeout;
    }
}
