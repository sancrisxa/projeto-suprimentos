package br.com.tjro.supribackend.util;

import br.com.tjro.supribackend.Exceptions.TokenInvalidoException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Service
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${egesp-api.sub}")
    private String sub;
    @Value("${egesp-api.mat}")
    private String mat;
    @Value("${egesp-api.key}")
    private String key;
    @Value("${egesp-api.exp}")
    private String exp;

    public String generateToken() throws Exception {
        String token;

        try {
            token = Jwts.builder()
                    .setSubject(this.sub)
                    .claim("mat", this.mat)
                    .setExpiration(new Date(System.currentTimeMillis() + Integer.parseInt(this.exp)))
                    .signWith(SignatureAlgorithm.HS512, this.key.getBytes())
                    .compact();
        } catch (Exception exception) {
            throw new TokenInvalidoException("Erro ao gerar o token.");
        }

        this.validarToken(token, this.key.getBytes());

        return token;
    }

    public void validarToken(String token, byte[] secretKey) throws Exception {

        SecretKey originalKey = new SecretKeySpec(secretKey, 0, secretKey.length, "AES");

        String[] chunks = token.split("\\.");

        SignatureAlgorithm sa = SignatureAlgorithm.HS512;

        String tokenWithoutSignature = chunks[0] + "." + chunks[1];
        String signature = chunks[2];

        DefaultJwtSignatureValidator validator = new DefaultJwtSignatureValidator(sa, originalKey);

        if (!validator.isValid(tokenWithoutSignature, signature)) {
            throw new TokenInvalidoException("Não foi possível verificar a integridade do token!");
        }
    }
}
