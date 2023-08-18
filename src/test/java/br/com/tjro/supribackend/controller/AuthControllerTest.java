package br.com.tjro.supribackend.controller;

import br.com.tjro.supribackend.dto.LoginDto;
import br.com.tjro.supribackend.dto.RefreshTokenDto;
import br.com.tjro.supribackend.service.AutenticacaoSSOService;
import br.com.tjro.supribackend.util.MessageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AutenticacaoSSOService autenticacaoSSOService;
    @Mock
    private MessageService messageService;
    @InjectMocks
    private AuthController autenticacaoController;

    @Test
    void autenticar() {
        assertDoesNotThrow(() -> autenticacaoController.login(new LoginDto("teste", "teste")));
        assertDoesNotThrow(() -> autenticacaoSSOService.autenticarSSO(new LoginDto("teste", "teste")));
    }

    @Test
    void logout() {
        assertDoesNotThrow(() -> autenticacaoController.logout(new RefreshTokenDto("1")));
        assertDoesNotThrow(() -> autenticacaoSSOService.logoutSSO(new RefreshTokenDto("1")));
    }

    @Test
    void refreshToken() {
        assertDoesNotThrow(() -> autenticacaoController.refreshToken(new RefreshTokenDto("1")));
        assertDoesNotThrow(() -> autenticacaoSSOService.refreshTokenSSO(new RefreshTokenDto("1")));
    }
}
