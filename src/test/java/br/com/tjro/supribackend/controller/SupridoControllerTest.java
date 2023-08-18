package br.com.tjro.supribackend.controller;

import br.com.tjro.supribackend.dto.ResponseInsertDto;
import br.com.tjro.supribackend.dto.SupridoDto;
import br.com.tjro.supribackend.service.SupridoServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class SupridoControllerTest {

    @Mock
    SupridoServiceImpl supridoService;
    @Mock
    Pageable pageable;
    @InjectMocks
    SupridoController supridoController;

    private final String MATRICULA_SUPRIDO = "12345678";

    @Test
    @DisplayName("Deve retornar status Ok ao Listar todos os supridos")
    public void listarSuprido() throws Exception {

        List<SupridoDto> supridoDtos = new ArrayList<>();
        Mockito.when(this.supridoService.listarSuprido(pageable)).thenReturn(new PageImpl<>(supridoDtos));
        ResponseEntity<Page<SupridoDto>> responseEntity = this.supridoController.listarSuprido(pageable);

        Mockito.verify(this.supridoService, Mockito.times(1)).listarSuprido(pageable);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Deve retornar status Ok ao Buscar suprido por matricula")
    public void buscarSupridoMatricula() {

        SupridoDto supridoDto = new SupridoDto();
        supridoDto.setMatricula(MATRICULA_SUPRIDO);
        Mockito.when(this.supridoService.buscarSupridoMatricula(MATRICULA_SUPRIDO)).thenReturn(supridoDto);
        ResponseEntity<SupridoDto> responseEntity = this.supridoController.buscarSupridoMatricula(MATRICULA_SUPRIDO);

        Mockito.verify(this.supridoService, Mockito.times(1)).buscarSupridoMatricula(MATRICULA_SUPRIDO);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(MATRICULA_SUPRIDO, responseEntity.getBody().getMatricula());
    }
}
