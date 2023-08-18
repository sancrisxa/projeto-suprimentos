package br.com.tjro.supribackend.controller;

import br.com.tjro.supribackend.dto.*;
import br.com.tjro.supribackend.service.ElementoDespesaServiceImpl;;
import br.com.tjro.supribackend.service.ParametroSistemaService;
import br.com.tjro.supribackend.service.SolicitacaoSuprimentoServiceImpl;
import br.com.tjro.supribackend.service.SupridoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class SolicitacaoSuprimentoControllerTest {

    @InjectMocks
    SolicitacaoSuprimentoController solicitacaoSuprimentoController;

    @Mock
    SolicitacaoSuprimentoServiceImpl solicitacaoSuprimentoService;

    @Mock
    Pageable pageable;

    @Mock
    ElementoDespesaServiceImpl elementoDespesaService;

    @Mock
    SupridoService supridoService;

    @Mock
    ModelMapper mapper;

    @Mock
    ParametroSistemaService parametroSistemaService;

    @Test
    public void salvarSolicitacaoSuprimento() {

        SolicitacaoSuprimentoDto solicitacaoSuprimentoDto = new SolicitacaoSuprimentoDto();
        solicitacaoSuprimentoDto.setValorTotal(new BigDecimal(10.00));

        SolicitacaoSuprimentoDto SolicitacaoSuprimentoDtoSaved = new SolicitacaoSuprimentoDto();

        List<ParametroSistemaDto> parametroSistemaDtoList = new ArrayList<>();
        ParametroSistemaDto parametroSistemaDto = new ParametroSistemaDto();
        parametroSistemaDto.setTetoSuprimento(new BigDecimal(5.00));
        parametroSistemaDtoList.add(parametroSistemaDto);

        Mockito.when(this.parametroSistemaService.listarParametroSistema()).thenReturn(parametroSistemaDtoList);

        List<ElementoDespesaDto> elementoDespesaDtos = new ArrayList<>();
        elementoDespesaDtos.add(new ElementoDespesaDto());
        solicitacaoSuprimentoDto.setElementoDespesaList(elementoDespesaDtos);
        Mockito.when(this.solicitacaoSuprimentoService.salvarEditarSolicitacaoSuprimento(solicitacaoSuprimentoDto, null)).thenReturn(SolicitacaoSuprimentoDtoSaved);
        ResponseEntity<ResponseInsertDto> responseInsertDtoResponseEntity = this.solicitacaoSuprimentoController.salvarSolicitacaoSuprimento(solicitacaoSuprimentoDto);

        Assertions.assertEquals(HttpStatus.CREATED, responseInsertDtoResponseEntity.getStatusCode());
    }

    @Test
    public void editarSolicitacaoSuprimento() {

        SolicitacaoSuprimentoDto solicitacaoSuprimentoDto = new SolicitacaoSuprimentoDto();
        solicitacaoSuprimentoDto.setValorTotal(new BigDecimal(10.00));

        List<ParametroSistemaDto> parametroSistemaDtoList = new ArrayList<>();
        ParametroSistemaDto parametroSistemaDto = new ParametroSistemaDto();
        parametroSistemaDto.setTetoSuprimento(new BigDecimal(5.00));
        parametroSistemaDtoList.add(parametroSistemaDto);

        Mockito.when(this.parametroSistemaService.listarParametroSistema()).thenReturn(parametroSistemaDtoList);

        SolicitacaoSuprimentoDto SolicitacaoSuprimentoDtoSaved = new SolicitacaoSuprimentoDto();
        List<ElementoDespesaDto> elementoDespesaDtos = new ArrayList<>();
        elementoDespesaDtos.add(new ElementoDespesaDto());
        solicitacaoSuprimentoDto.setElementoDespesaList(elementoDespesaDtos);
        Mockito.when(this.solicitacaoSuprimentoService.salvarEditarSolicitacaoSuprimento(solicitacaoSuprimentoDto, 1L)).thenReturn(SolicitacaoSuprimentoDtoSaved);
        ResponseEntity<SolicitacaoSuprimentoDto> responseInsertDtoResponseEntity = this.solicitacaoSuprimentoController.editarSolicitacaoSuprimento(solicitacaoSuprimentoDto, 1L);

        Assertions.assertEquals(HttpStatus.OK, responseInsertDtoResponseEntity.getStatusCode());
    }

    @Test
    public void listarSolicitacaoSuprimento() {
        ResponseEntity<Page<SolicitacaoSuprimentoDto>> pageResponseEntity = this.solicitacaoSuprimentoController.listarSolicitacaoSuprimento(pageable);
        Assertions.assertEquals(HttpStatus.OK, pageResponseEntity.getStatusCode());
    }

    @Test
    public void listarSolicitacaoSuprimentoByIdComElementosDeDespesas() {

        SolicitacaoSuprimentoDto solicitacaoSuprimentoDto = new SolicitacaoSuprimentoDto();
        solicitacaoSuprimentoDto.setIdSolicitacaoSuprimento(1L);
        solicitacaoSuprimentoDto.setMatriculaSuprido("matricula");
        solicitacaoSuprimentoDto.setNomeSuprido("nome");

        List<ElementoDespesaDto> elementoDespesaDtoList = new ArrayList<>();
        elementoDespesaDtoList.add(new ElementoDespesaDto());

        solicitacaoSuprimentoDto.setElementoDespesaList(elementoDespesaDtoList);

        Mockito.when(this.solicitacaoSuprimentoService.findById(1L)).thenReturn(solicitacaoSuprimentoDto);

        SupridoDto supridoDto = new SupridoDto();

        Mockito.when(this.supridoService.buscarSupridoMatricula("matricula")).thenReturn(supridoDto);

        ResponseEntity<SolicitacaoSuprimentoDto> solicitacaoSuprimentoDtoResponseEntity = this.solicitacaoSuprimentoController.listarSolicitacaoSuprimentoByIdComElementosDeDespesas(1L);

        Assertions.assertNotNull(solicitacaoSuprimentoDtoResponseEntity);
        Assertions.assertNotNull(solicitacaoSuprimentoDtoResponseEntity.getBody().getElementoDespesaList());
        Assertions.assertEquals("matricula", solicitacaoSuprimentoDtoResponseEntity.getBody().getMatriculaSuprido());
    }

    @Test
    public void removerSolicitacao() {
        ResponseEntity responseEntity = this.solicitacaoSuprimentoController.removerSolicitacao(1L);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
}
