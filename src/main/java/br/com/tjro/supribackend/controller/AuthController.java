package br.com.tjro.supribackend.controller;

import br.com.tjro.supribackend.dto.LoginDto;
import br.com.tjro.supribackend.dto.LoginInfoDto;
import br.com.tjro.supribackend.dto.RefreshTokenDto;
import br.com.tjro.supribackend.dto.SsoTokenDto;
import br.com.tjro.supribackend.service.AutenticacaoSSOService;
import br.com.tjro.supribackend.util.JwtUtil;
import br.com.tjro.supribackend.util.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AutenticacaoSSOService autenticacaoSSOService;
    private final MessageService messageService;

    @PostMapping("/login")
    public ResponseEntity<LoginInfoDto> login(@Valid @RequestBody LoginDto login) throws Exception {
        LoginInfoDto loginInfoDto = autenticacaoSSOService.autenticarSSO(login);
        return ResponseEntity.ok(loginInfoDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenDto logout) {
        autenticacaoSSOService.logoutSSO(logout);
        return ResponseEntity.ok(messageService.message("sss.logout.sucess"));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<SsoTokenDto> refreshToken(@Valid @RequestBody RefreshTokenDto refresh) {
        SsoTokenDto ssoTokenDto = autenticacaoSSOService.refreshTokenSSO(refresh);
        return ResponseEntity.ok(ssoTokenDto);
    }
}
