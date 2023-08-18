package br.com.tjro.supribackend.controller;

import br.com.tjro.supribackend.dto.NotaComItemPublicaDto;
import br.com.tjro.supribackend.projection.NotaApiPublicProjection;
import br.com.tjro.supribackend.service.ApiPublicService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class ApiPublicControllerTest {

    @InjectMocks
    ApiPublicController apiPublicController;

    @Mock
    ApiPublicService apiPublicService;

    @Mock
    Pageable pageable;
    @Mock
    Page<NotaApiPublicProjection> notas;

    @Test
    public void buscarNotaPorAnoMes() {

        Mockito.when(this.apiPublicService.buscarNotaPorAnoMes(pageable, "2023", "06")).thenReturn(notas);

        ResponseEntity<Page<NotaComItemPublicaDto>> listResponseEntity = this.apiPublicController.buscarNotaPorAnoMes(pageable, "2023", "06");

        Assertions.assertEquals(HttpStatus.OK, listResponseEntity.getStatusCode());
    }
}
