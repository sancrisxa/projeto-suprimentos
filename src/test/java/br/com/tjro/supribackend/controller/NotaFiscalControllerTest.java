package br.com.tjro.supribackend.controller;

import br.com.tjro.supribackend.dto.NotaFiscalDto;
import br.com.tjro.supribackend.dto.ResponseInsertDto;
import br.com.tjro.supribackend.projection.NotaProjection;
import br.com.tjro.supribackend.projection.NotaProjectionAtivo;
import br.com.tjro.supribackend.service.NotaFiscalServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class NotaFiscalControllerTest {

    @Mock
    NotaProjection notaProjection;

    @Mock
    Pageable pageable;

    @Mock
    NotaFiscalServiceImpl notaFiscalService;

    @InjectMocks
    NotaFiscalController notaFiscalController;

    @Test
    void salvarContrato() {

        NotaFiscalDto notaFiscalDto = new NotaFiscalDto();

        ResponseEntity<ResponseInsertDto> responseEntity = this.notaFiscalController.salvarNotaFiscal(notaFiscalDto);

        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void listarNotaFiscal() {
        List<NotaProjectionAtivo> notaProjectionList = new ArrayList<>();
        Page<NotaProjectionAtivo> notaFiscalDtos = new PageImpl<>(notaProjectionList);
        Mockito.when(this.notaFiscalService.listarNotaFiscal(pageable)).thenReturn(notaFiscalDtos);

        ResponseEntity<Page<NotaProjectionAtivo>> responseEntity = this.notaFiscalController.listarNotaFiscal(pageable);

        Mockito.verify(this.notaFiscalService, Mockito.times(1)).listarNotaFiscal(pageable);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void editarNotaFiscal() {
        NotaFiscalDto notaFiscalDto = new NotaFiscalDto();

        ResponseEntity<ResponseInsertDto> responseEntity = this.notaFiscalController.editarNotaFiscal(notaFiscalDto, 1L);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void consultarNotaFiscal() {

        // F.I.R.S.T
        // FAST
        // INDEPENDENT
        // REPEATABLE
        // SELF-VALIDATING
        // TIMELY

        // Arrange
        NotaFiscalDto notaFiscalDto = new NotaFiscalDto();
        notaFiscalDto.setNumeroDocumentoFiscal("123");
        Mockito.when(this.notaFiscalService.consultarNotaFiscal(1L)).thenReturn(notaFiscalDto);

        // Action
        ResponseEntity<NotaFiscalDto> responseEntity = this.notaFiscalController.consultarNotaFiscal(1L);

        // Assertion
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals("123", responseEntity.getBody().getNumeroDocumentoFiscal());
    }
}
