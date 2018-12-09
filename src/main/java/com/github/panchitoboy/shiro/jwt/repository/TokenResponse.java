package com.github.panchitoboy.shiro.jwt.repository;

public class TokenResponse {

    public TokenResponse() {
    }

    public TokenResponse(Principal principal, String token) {
        this.principal = principal;
        this.token = token;
    }

    private String token;

    private Principal principal;

    public String getToken() {
        return token;
    }

    public Principal getPrincipal() {
        return principal;
    }

}
