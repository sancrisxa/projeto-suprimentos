package br.com.tjro.supribackend.service;


import br.com.tjro.supribackend.dto.ParametroSistemaDto;
import br.com.tjro.supribackend.model.ParametroSistema;
import br.com.tjro.supribackend.repository.ParametroSistemaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ParametroSistemaServiceTest {

    @Mock
    ParametroSistemaRepository parametroSistemaRepository;

    @InjectMocks
    ParametroSistemaServiceImpl parametroSistemaService;

    @Test
    public void salvarEditarParametroSistema() {

        ParametroSistemaDto parametroSistemaDto = new ParametroSistemaDto();
        ParametroSistema parametroSistema = new ParametroSistema();
        Mockito.when(parametroSistemaRepository.save(parametroSistema)).thenReturn(parametroSistema);

        this.parametroSistemaService.salvarEditarParametroSistema(parametroSistemaDto, null);
    }

    @Test
    public void salvarEditarParametroSistemaComId() {

        ParametroSistemaDto parametroSistemaDto = new ParametroSistemaDto();
        ParametroSistema parametroSistema = new ParametroSistema();
        parametroSistema.setIdParametroSistema(1L);
        Mockito.when(parametroSistemaRepository.save(parametroSistema)).thenReturn(parametroSistema);

        this.parametroSistemaService.salvarEditarParametroSistema(parametroSistemaDto, 1L);
    }

    @Test
    public void listarParametroSistema() {
        List<ParametroSistema> parametroSistemas = new ArrayList<>();
        Mockito.when(parametroSistemaRepository.findAll()).thenReturn(parametroSistemas);

        this.parametroSistemaService.listarParametroSistema();
    }

}
