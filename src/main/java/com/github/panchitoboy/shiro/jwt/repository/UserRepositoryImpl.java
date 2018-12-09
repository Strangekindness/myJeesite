package com.github.panchitoboy.shiro.jwt.repository;

import java.security.SecureRandom;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

import javax.inject.Singleton;

import org.springframework.stereotype.Component;

import com.github.panchitoboy.shiro.jwt.verifier.MACVerifierExtended;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
@Component
@Singleton
public class UserRepositoryImpl implements UserRepository {
	private byte[] sharedKey;
	    
    public byte[] generateSharedKey() {
        SecureRandom random = new SecureRandom();
        byte[] sharedKey = new byte[32];
        random.nextBytes(sharedKey);
        return sharedKey;
    }

    public long getExpirationDate() {
        //return 1000 * 60 * 60 * 24 * 5;
    	return 1000 * 60 * 60;
    }

    @Override
    public String getIssuer() {
        return "issuer";
    }

    @Override
    public byte[] getSharedKey() {
        if (sharedKey == null) {
            sharedKey = generateSharedKey();
        }
        return sharedKey;
    }

    public TokenResponse createToken(Principal principal) {
    	TokenResponse response = new TokenResponse(principal, createToken((Object)principal));
        return response;
    }

    public String createToken(Object user) {
        try {
            JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();

            builder.issuer(getIssuer());
            builder.subject(user.toString());
            builder.issueTime(new Date());
            builder.notBeforeTime(new Date());
            builder.expirationTime(new Date(new Date().getTime() + getExpirationDate()));
            builder.jwtID(UUID.randomUUID().toString());

            JWTClaimsSet claimsSet = builder.build();
            JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

            Payload payload = new Payload(claimsSet.toJSONObject());

            JWSObject jwsObject = new JWSObject(header, payload);

            JWSSigner signer = new MACSigner(getSharedKey());
            jwsObject.sign(signer);
            return jwsObject.serialize();
        } catch (JOSEException ex) {
            return null;
        }
    }

    public boolean validateToken(String token) {

        try {
            SignedJWT signed = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifierExtended(getSharedKey(), signed.getJWTClaimsSet());
            return signed.verify(verifier);
        } catch (ParseException ex) {
            return false;
        } catch (JOSEException ex) {
            return false;
        }

    }
}
