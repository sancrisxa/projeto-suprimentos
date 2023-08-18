package br.com.tjro.supribackend.service;

import br.com.tjro.supribackend.dto.SolicitacaoSuprimentoDto;
import br.com.tjro.supribackend.dto.SupridoDto;
import br.com.tjro.supribackend.enums.StatusSolicitacaoSuprimento;
import br.com.tjro.supribackend.model.SolicitacaoSuprimento;
import br.com.tjro.supribackend.model.Suprido;
import br.com.tjro.supribackend.repository.SolicitacaoSuprimentoRepository;
import br.com.tjro.supribackend.repository.SupridoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class SolicitacaoSuprimentoServiceTest {

    @Mock
    SupridoRepository supridoRepository;
    @Mock
    SolicitacaoSuprimentoRepository solicitacaoSuprimentoRepository;
    @Mock
    ModelMapper modelMapper;
    @Mock
    Pageable pageable;
    @Mock
    SupridoServiceImpl supridoService;
    @InjectMocks
    SolicitacaoSuprimentoServiceImpl solicitacaoSuprimentoService;


    @Test
    @DisplayName("Deve retornar a solicitacao ao salvar")
    public void salvarSolicitacao() {

        SupridoDto supridoDto = new SupridoDto();
        supridoDto.setMatricula("matricula");
        SolicitacaoSuprimento solicitacaoSuprimento = new SolicitacaoSuprimento();
        solicitacaoSuprimento.setMatriculaSuprido("matricula");
        SolicitacaoSuprimentoDto solicitacaoSuprimentoDto = new SolicitacaoSuprimentoDto();
        solicitacaoSuprimentoDto.setMatriculaSuprido("matricula");

        Mockito.when(this.modelMapper.map(solicitacaoSuprimentoDto, SolicitacaoSuprimento.class)).thenReturn(solicitacaoSuprimento);
        Mockito.when(this.modelMapper.map(solicitacaoSuprimento, SolicitacaoSuprimentoDto.class)).thenReturn(solicitacaoSuprimentoDto);
        Mockito.when(this.supridoService.buscarSupridoMatricula("matricula")).thenReturn(supridoDto);
        Mockito.when(this.solicitacaoSuprimentoRepository.save(solicitacaoSuprimento)).thenReturn(solicitacaoSuprimento);


        solicitacaoSuprimentoDto.setMatriculaSuprido("matricula");
        SolicitacaoSuprimentoDto suprimentoDtoSaved = this.solicitacaoSuprimentoService.salvarEditarSolicitacaoSuprimento(solicitacaoSuprimentoDto, null);

        Assertions.assertNotNull(suprimentoDtoSaved);
        Assertions.assertEquals("matricula", suprimentoDtoSaved.getMatriculaSuprido());
    }

    @Test
    @DisplayName("Deve retornar a solicitacao dado um id")
    public void findById() {

        // Arrange
        SolicitacaoSuprimento solicitacaoSuprimento = new SolicitacaoSuprimento();
        solicitacaoSuprimento.setIdSolicitacaoSuprimento(1l);
        solicitacaoSuprimento.setMatriculaSuprido("123");

        Optional<SolicitacaoSuprimento> optionalSolicitacaoSuprimento = Optional.ofNullable(solicitacaoSuprimento);

        Mockito.when(this.solicitacaoSuprimentoRepository.findById(1L)).thenReturn(optionalSolicitacaoSuprimento);

        SolicitacaoSuprimentoDto solicitacaoSuprimentoDto = new SolicitacaoSuprimentoDto();
        solicitacaoSuprimentoDto.setIdSolicitacaoSuprimento(1L);
        solicitacaoSuprimentoDto.setMatriculaSuprido("123");
        Mockito.when(modelMapper.map(optionalSolicitacaoSuprimento, SolicitacaoSuprimentoDto.class)).thenReturn(solicitacaoSuprimentoDto);

        // Action
        SolicitacaoSuprimentoDto suprimentoServiceFound = this.solicitacaoSuprimentoService.findById(1L);

        // Assertions
        Assertions.assertNotNull(suprimentoServiceFound);
        Assertions.assertEquals(1L, suprimentoServiceFound.getIdSolicitacaoSuprimento());
        Assertions.assertEquals("123", suprimentoServiceFound.getMatriculaSuprido());
    }

    @Test
    public void listarSuprimentoSolicitacao() {

        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), 1, Sort.by(Sort.Direction.ASC,"dataSolicitacao"));

        List<SolicitacaoSuprimento> solicitacaoSuprimentos = new ArrayList<>();
        SolicitacaoSuprimento solicitacaoSuprimento = new SolicitacaoSuprimento();
        solicitacaoSuprimentos.add(solicitacaoSuprimento);

        SolicitacaoSuprimentoDto suprimentoDto = new SolicitacaoSuprimentoDto();
        suprimentoDto.setStatusSolicitacaoSuprimento(StatusSolicitacaoSuprimento.SOLICITADO);

        Mockito.when(modelMapper.map(solicitacaoSuprimento, SolicitacaoSuprimentoDto.class)).thenReturn(suprimentoDto);
        Mockito.when(this.solicitacaoSuprimentoRepository.findAll(pageRequest)).thenReturn(new PageImpl<>(solicitacaoSuprimentos));

        Page<SolicitacaoSuprimentoDto> solicitacaoSuprimentoDtos = this.solicitacaoSuprimentoService.listarSuprimentoSolicitacao(pageRequest);

        Mockito.verify(this.solicitacaoSuprimentoRepository, Mockito.times(1)).findAll(pageRequest);
        Assertions.assertNotNull(solicitacaoSuprimentoDtos);
    }

}
