package br.com.tjro.supribackend.controller;

import br.com.tjro.supribackend.dto.ParametroSistemaDto;
import br.com.tjro.supribackend.service.ParametroSistemaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
public class ParametroSistemaControllerTest {

    @Mock
    ParametroSistemaService parametroSistemaService;
    @InjectMocks
    ParametroSistemaController parametroSistemaController;

    @Test
    void salvarParametroSistema() {
        assertDoesNotThrow(() -> parametroSistemaService.salvarEditarParametroSistema(new ParametroSistemaDto(), null));
        assertDoesNotThrow(() -> parametroSistemaController.salvarParametroSistema(new ParametroSistemaDto()));
    }

    @Test
    void editarParametroSistema() {
        assertDoesNotThrow(() -> parametroSistemaService.salvarEditarParametroSistema(new ParametroSistemaDto(), 1L));
        assertDoesNotThrow(() -> parametroSistemaController.editarParametroSistema(new ParametroSistemaDto(), 1L));
    }

    @Test
    public void listarParametroSistema() {
        assertDoesNotThrow(() -> parametroSistemaService.listarParametroSistema());
        assertDoesNotThrow(() -> parametroSistemaController.listarParametroSistema());
    }
}
