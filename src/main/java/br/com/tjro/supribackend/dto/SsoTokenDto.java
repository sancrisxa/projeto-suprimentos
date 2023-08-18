package br.com.tjro.supribackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SsoTokenDto {
    private String accessToken;
    private Integer expiresIn;
    private Integer refreshExpiresIn;
    private String refreshToken;
    private String tokenType;
    private String idToken;
    private Integer notBeforePolicy;
    private String sessionState;
    private String scope;
}
