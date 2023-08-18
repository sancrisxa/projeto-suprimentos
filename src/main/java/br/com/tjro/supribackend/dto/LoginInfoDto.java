package br.com.tjro.supribackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginInfoDto {
    private Long idUsuario;
    private String nomeUsuario;
    private String matricula;
    private String egespApiToken;
    private SsoTokenDto token;


}
