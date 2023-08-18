package br.com.tjro.supribackend.service;

import br.com.tjro.supribackend.Exceptions.DatabaseErrorException;
import br.com.tjro.supribackend.Exceptions.SupridoInexistenteException;
import br.com.tjro.supribackend.dto.SupridoDto;
import br.com.tjro.supribackend.model.Suprido;
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
public class SupridoServiceTest {

    @Mock
    private ModelMapper mapper;
    @Mock
    private Pageable pageable;
    @Mock
    private SupridoRepository supridoRepository;
    @InjectMocks
    private SupridoServiceImpl supridoService;

    @Test
    @DisplayName("Dever retornar a Lista de todos os supridos")
    public void listarSuprido() {

        Suprido suprido = new Suprido();
        List<Suprido> supridos = new ArrayList<>();
        supridos.add(suprido);

        Mockito.when(this.supridoRepository.findAllBaseEgesp(pageable)).thenReturn(new PageImpl<>(supridos));

        Page<SupridoDto> supridoDtos = this.supridoService.listarSuprido(pageable);

        Mockito.verify(this.supridoRepository, Mockito.times(1)).findAllBaseEgesp(pageable);
        Assertions.assertNotNull(supridoDtos);
    }

    @Test
    @DisplayName("Deve retornar o suprido por matricula")
    public void buscarSupridoMatricula() {

        Optional<Suprido> suprido = Optional.of(new Suprido());
        Mockito.when(this.supridoRepository.findByMatricula("matricula")).thenReturn(suprido);
        SupridoDto supridoDtoMapper = new SupridoDto();
        supridoDtoMapper.setNome("nome");
        supridoDtoMapper.setMatricula("matricula");
        Mockito.when(mapper.map(suprido, SupridoDto.class)).thenReturn(supridoDtoMapper);

        SupridoDto supridoDto =  this.supridoService.buscarSupridoMatricula("matricula");

        Assertions.assertNotNull(supridoDto);
        Assertions.assertEquals("nome", supridoDto.getNome());
        Assertions.assertEquals("matricula", supridoDto.getMatricula());
    }

    @Test
    @DisplayName("Deve retornar excecao se não encontrar o suprido")
    public void buscarSupridoMatriculaSupridoInexistenteException() {
       Assertions.assertThrows(SupridoInexistenteException.class, () -> {
           //Code under test
           this.supridoService.buscarSupridoMatricula("matricula");
       });
    }

    @Test
    @DisplayName("Deve retornar uma excecao se houver erro na busca no banco")
    public void listarSupridoDatabaseErrorException () {
        Mockito.doThrow(new DatabaseErrorException("Erro ao salvar ocorrencia.")).when(this.supridoRepository).findAllBaseEgesp(pageable);

        Assertions.assertThrows(DatabaseErrorException.class, () ->{
            this.supridoService.listarSuprido(pageable);
        });
    }
}
