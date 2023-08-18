package br.com.tjro.supribackend.service;

import br.com.tjro.supribackend.Exceptions.DatabaseErrorException;
import br.com.tjro.supribackend.Exceptions.OcorrenciaNotFoundException;
import br.com.tjro.supribackend.dto.FornecedorDto;
import br.com.tjro.supribackend.dto.OcorrenciaDto;
import br.com.tjro.supribackend.enums.StatusOcorrencia;
import br.com.tjro.supribackend.model.Fornecedor;
import br.com.tjro.supribackend.model.Ocorrencia;
import br.com.tjro.supribackend.repository.OcorrenciaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class OcorrenciaServiceTest {

    @InjectMocks
    OcorrenciaServiceImpl ocorrenciaService;

    @Mock
    OcorrenciaRepository ocorrenciaRepository;

    @Mock
    ModelMapper modelMapper;

    @Mock
    Pageable pageable;

    @Mock
    FornecedorService fornecedorService;

    private final Long ID = 1L;

    @Test
    @DisplayName("Ao salvar deve retornar uma ocorrencia salva")
    void salvarOcorrencia() {

        FornecedorDto fornecedorDto = new FornecedorDto();
        Fornecedor fornecedor = new Fornecedor();

        OcorrenciaDto ocorrenciaDto = new OcorrenciaDto();
        ocorrenciaDto.setIdFornecedor(ID);
        Ocorrencia ocorrencia = new Ocorrencia();

        Mockito.when(this.fornecedorService.consultarFornecedor(ID)).thenReturn(fornecedorDto);
        Mockito.when(this.modelMapper.map(fornecedorDto, Fornecedor.class)).thenReturn(fornecedor);
        Mockito.when(this.modelMapper.map(ocorrencia, OcorrenciaDto.class)).thenReturn(ocorrenciaDto);
        Mockito.when(this.modelMapper.map(ocorrenciaDto, Ocorrencia.class)).thenReturn(ocorrencia);
        Mockito.when(this.ocorrenciaRepository.save(ocorrencia)).thenReturn(ocorrencia);

        OcorrenciaDto ocorrenciaDtoSaved = this.ocorrenciaService.salvarOcorrencia(ocorrenciaDto);

        Mockito.verify(this.ocorrenciaRepository, Mockito.times(1)).save(ocorrencia);
        Assertions.assertNotNull(ocorrenciaDtoSaved);
    }

    @Test
    @DisplayName("Deve lançar exceção ao salvar no banco")
    public void salvarOcorrenciaDatabaseErrorException() {

        Ocorrencia ocorrencia = new Ocorrencia();
        OcorrenciaDto ocorrenciaDto = new OcorrenciaDto();

        Mockito.when(modelMapper.map(ocorrenciaDto, Ocorrencia.class)).thenReturn(ocorrencia);

        Mockito.doThrow(new DatabaseErrorException("Erro ao salvar ocorrencia.")).when(this.ocorrenciaRepository).save(ocorrencia);

        Assertions.assertThrows(DatabaseErrorException.class, () ->{
            this.ocorrenciaService.salvarOcorrencia(ocorrenciaDto);
        });
    }

    @Test
    @DisplayName("Deve retornar uma lista de ocorrencias")
    void listarOcorrencias() {

        OcorrenciaDto ocorrenciaDto = new OcorrenciaDto();
        Ocorrencia ocorrencia = new Ocorrencia();
        FornecedorDto fornecedorDto = new FornecedorDto();
        Fornecedor fornecedor = new Fornecedor();

        List<Ocorrencia> ocorrenciaList = new ArrayList<>();
        ocorrenciaList.add(ocorrencia);

        Mockito.when(this.modelMapper.map(ocorrencia, OcorrenciaDto.class)).thenReturn(ocorrenciaDto);
        Mockito.when( this.modelMapper.map(fornecedorDto, Fornecedor.class)).thenReturn(fornecedor);
        Mockito.when(this.fornecedorService.consultarFornecedor(ID)).thenReturn(fornecedorDto);
        Mockito.when(this.ocorrenciaRepository.findByFornecedor(pageable, fornecedor)).thenReturn(new PageImpl<>(ocorrenciaList));

        Page<OcorrenciaDto> ocorrenciaDtos = this.ocorrenciaService.listarOcorrencias(pageable, ID);

        Mockito.verify(this.ocorrenciaRepository, Mockito.times(1)).findByFornecedor(pageable, fornecedor);

        Assertions.assertNotNull(ocorrenciaDtos);
    }

    @Test
    @DisplayName("Deve lançar exceção ao listar as ocorrencias")
    public void listarOcorrenciasDatabaseErrorException() {

        Fornecedor fornecedor = new Fornecedor();
        FornecedorDto fornecedorDto = new FornecedorDto();

        Mockito.when(this.fornecedorService.consultarFornecedor(ID)).thenReturn(fornecedorDto);
        Mockito.when(this.modelMapper.map(fornecedorDto, Fornecedor.class)).thenReturn(fornecedor);
        Mockito.doThrow(new DatabaseErrorException("Erro ao listar as ocorrencias.")).when(this.ocorrenciaRepository).findByFornecedor(pageable, fornecedor);

        Assertions.assertThrows(DatabaseErrorException.class, () -> {
            this.ocorrenciaService.listarOcorrencias(pageable, ID);
        });

    }

    @Test
    @DisplayName("Ao editar deve retornar uma ocorrencia com os campos editados")
    void editarOcorrencia() {
        OcorrenciaDto ocorrenciaDto = new OcorrenciaDto();
        ocorrenciaDto.setDescricao("descricao");
        ocorrenciaDto.setStatusOcorrencia(StatusOcorrencia.ADVERTENCIA);
        ocorrenciaDto.setPrazo(10);
        ocorrenciaDto.setDataOcorrencia(LocalDate.now());
        Ocorrencia ocorrencia = new Ocorrencia();

        Mockito.when(this.modelMapper.map(ocorrencia, OcorrenciaDto.class)).thenReturn(ocorrenciaDto);
        Mockito.when(this.modelMapper.map(Optional.of(ocorrencia), Ocorrencia.class)).thenReturn(ocorrencia);
        Mockito.when(this.ocorrenciaRepository.findById(ID)).thenReturn(Optional.of(ocorrencia));
        Mockito.when(this.ocorrenciaRepository.save(ocorrencia)).thenReturn(ocorrencia);

        OcorrenciaDto ocorrenciaDtoSaved = this.ocorrenciaService.editarOcorrencia(ocorrenciaDto, 1L);

        Mockito.verify(this.ocorrenciaRepository, Mockito.times(1)).save(ocorrencia);
        Assertions.assertNotNull(ocorrenciaDtoSaved);
    }

    @DisplayName("Deve lançar exceção OcorrenciaNotFoundException ao buscar ocorrencia")
    @Test
    public void editarOcorrenciaOcorrenciaNotFoundException() {
        OcorrenciaDto ocorrenciaDto = new OcorrenciaDto();
        Mockito.when(this.ocorrenciaRepository.findById(ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(OcorrenciaNotFoundException.class, () -> {
           this.ocorrenciaService.editarOcorrencia(ocorrenciaDto, ID);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção DatabaseErrorException ao editar a ocorrência")
    public void editarOcorrenciaDatabaseErrorException() {
        Ocorrencia ocorrencia = new Ocorrencia();
        OcorrenciaDto ocorrenciaDto = new OcorrenciaDto();
        ocorrenciaDto.setDescricao("descricao");


        Mockito.when(this.modelMapper.map(Optional.of(ocorrencia), Ocorrencia.class)).thenReturn(ocorrencia);
        Mockito.when(this.ocorrenciaRepository.findById(ID)).thenReturn(Optional.of(ocorrencia));
        Mockito.doThrow(new DatabaseErrorException("Erro ao editar ocorrencia.")).when(this.ocorrenciaRepository).save(ocorrencia);

        Assertions.assertThrows(DatabaseErrorException.class, () ->{
            this.ocorrenciaService.editarOcorrencia(ocorrenciaDto, ID);
        });
    }

    @Test
    @DisplayName("Deve excluir uma ocorrencia dado o ID da ocorrencia")
    void excluirOcorrencia() {

        Ocorrencia ocorrencia = new Ocorrencia();
        ocorrencia.setIdOcorrencia(ID);

        Mockito.when(this.modelMapper.map(Optional.of(ocorrencia), Ocorrencia.class)).thenReturn(ocorrencia);
        Mockito.when(this.ocorrenciaRepository.findById(ID)).thenReturn(Optional.of(ocorrencia));
        Mockito.doNothing().when(this.ocorrenciaRepository).delete(ocorrencia);

        this.ocorrenciaService.excluirOcorrencia(ID);
    }

    @Test
    @DisplayName("Deve retornar uma ocorrencia dado um ID")
    void findByIdOcorrencia() {

        Ocorrencia ocorrencia = new Ocorrencia();
        OcorrenciaDto ocorrenciaDto = new OcorrenciaDto();

        Mockito.when(this.modelMapper.map(Optional.of(ocorrencia), OcorrenciaDto.class)).thenReturn(ocorrenciaDto);
        Mockito.when(this.ocorrenciaRepository.findById(ID)).thenReturn(Optional.of(ocorrencia));

        OcorrenciaDto ocorrenciaDtoFound = this.ocorrenciaService.findByIdOcorrencia(1L);

        Mockito.verify(this.ocorrenciaRepository).findById(ID);

        Assertions.assertNotNull(ocorrenciaDtoFound);
    }
}