package com.github.panchitoboy.shiro.jwt.repository;

import org.springframework.stereotype.Service;

@Service
public interface UserRepository {

    public byte[] generateSharedKey();

    public long getExpirationDate();

    public String getIssuer();

    public byte[] getSharedKey();

    public TokenResponse createToken(Principal principal);

    public String createToken(Object userId);

    public boolean validateToken(String token) ;
}
