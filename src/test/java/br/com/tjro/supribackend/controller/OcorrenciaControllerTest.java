package br.com.tjro.supribackend.controller;

import br.com.tjro.supribackend.dto.OcorrenciaDto;
import br.com.tjro.supribackend.dto.ResponseInsertDto;
import br.com.tjro.supribackend.service.OcorrenciaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class OcorrenciaControllerTest {

    @Mock
    Pageable pageable;

    @Mock
    OcorrenciaService ocorrenciaService;

    @InjectMocks
    OcorrenciaController ocorrenciaController;

    private final Long ID_OCORRENCIA = 1L;

    @Test
    @DisplayName("Deve retonar status 201 created")
    void cadastrarOcorrencia() {

        OcorrenciaDto ocorrenciaDto = new OcorrenciaDto();
        ocorrenciaDto.setIdOcorrencia(ID_OCORRENCIA);
        Mockito.when(this.ocorrenciaService.salvarOcorrencia(ocorrenciaDto)).thenReturn(ocorrenciaDto);

        ResponseEntity<ResponseInsertDto> responseEntity = this.ocorrenciaController.cadastrarOcorrencia(ocorrenciaDto);

        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Deve retornar status 200 ok ao listar as ocorrencias")
    void listarOcorrencias() {

        ResponseEntity<Page<OcorrenciaDto>> responseEntity = this.ocorrenciaController.listarOcorrencias(pageable, ID_OCORRENCIA);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Deve retornar status 200 ok ao editar a ocorrencia")
    void editarOcorrencia() {
        OcorrenciaDto ocorrenciaDto = new OcorrenciaDto();
        ResponseEntity<OcorrenciaDto> responseEntity = this.ocorrenciaController.editarOcorrencia(ocorrenciaDto, ID_OCORRENCIA);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Deve retornar status 204 ao excluir a ocorrencia")
    void excluirOcorrencia() {
        ResponseEntity responseEntity = this.ocorrenciaController.excluirOcorrencia(ID_OCORRENCIA);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Deve retornar status 200 ok ao editar a ocorrencia")
    void findByIdOcorrencia() {
        ResponseEntity<OcorrenciaDto> responseEntity = this.ocorrenciaController.findByIdOcorrencia(1L);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}