package br.com.tjro.supribackend.service;

import br.com.tjro.supribackend.dto.NotaFiscalDto;
import br.com.tjro.supribackend.model.Fornecedor;
import br.com.tjro.supribackend.model.NotaFiscal;
import br.com.tjro.supribackend.projection.NotaProjection;
import br.com.tjro.supribackend.projection.NotaProjectionAtivo;
import br.com.tjro.supribackend.repository.FornecedorRepository;
import br.com.tjro.supribackend.repository.NotaFiscalRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class NotaFiscalServiceTest {

    @Mock
    ModelMapper modelMapper;
    @Mock
    Pageable pageable;
    @Mock
    private FornecedorRepository fornecedorRepository;

    @Mock
    private NotaFiscalRepository notaFiscalRepository;

    @InjectMocks
    private NotaFiscalServiceImpl notaFiscalService;

    @Test
    public void salvarEditarNotaFiscal() {

        NotaFiscalDto notaFiscalDto = new NotaFiscalDto();
        notaFiscalDto.setIdFornecedor(1L);
        notaFiscalDto.setNumeroDocumentoFiscal("123");

        NotaFiscal notaFiscal = new NotaFiscal();
        notaFiscal.setNumeroDocumentoFiscal("123");

        Fornecedor fornecedor = new Fornecedor();

        Mockito.when(this.fornecedorRepository.findById(notaFiscalDto.getIdFornecedor())).thenReturn(Optional.of(fornecedor));
        Mockito.when(this.modelMapper.map(notaFiscalDto, NotaFiscal.class)).thenReturn(notaFiscal);
        Mockito.when(this.notaFiscalRepository.save(notaFiscal)).thenReturn(notaFiscal);
        Mockito.when(this.modelMapper.map(notaFiscal, NotaFiscalDto.class)).thenReturn(notaFiscalDto);

        NotaFiscalDto notaFiscalSaved = notaFiscalService.salvarEditarNotaFiscal(notaFiscalDto, null);

        Assertions.assertEquals("123", notaFiscalSaved.getNumeroDocumentoFiscal());
    }

    @Test
    public void salvarEditarNotaFiscalComId() {

        NotaFiscalDto notaFiscalDto = new NotaFiscalDto();
        notaFiscalDto.setIdFornecedor(1L);
        notaFiscalDto.setNumeroDocumentoFiscal("123");

        NotaFiscal notaFiscal = new NotaFiscal();
        notaFiscal.setNumeroDocumentoFiscal("123");

        Fornecedor fornecedor = new Fornecedor();

        Mockito.when(this.fornecedorRepository.findById(notaFiscalDto.getIdFornecedor())).thenReturn(Optional.of(fornecedor));
        Mockito.when(this.modelMapper.map(notaFiscalDto, NotaFiscal.class)).thenReturn(notaFiscal);
        Mockito.when(this.notaFiscalRepository.save(notaFiscal)).thenReturn(notaFiscal);
        Mockito.when(this.modelMapper.map(notaFiscal, NotaFiscalDto.class)).thenReturn(notaFiscalDto);

        NotaFiscalDto notaFiscalSaved = notaFiscalService.salvarEditarNotaFiscal(notaFiscalDto, 1L);

        Assertions.assertEquals("123", notaFiscalSaved.getNumeroDocumentoFiscal());
    }

    @Test
    @DisplayName("Lista todos as notas fiscais")
    public void listarNotasFiscais() {

        List<NotaProjectionAtivo> notaProjectionList = new ArrayList<>();
        Page<NotaProjectionAtivo> notaFiscalDtos = new PageImpl<>(notaProjectionList);
        Mockito.when(notaFiscalRepository.findAllNotasAtivas(pageable)).thenReturn(notaFiscalDtos);

        Page<NotaProjectionAtivo> notaProjectionAtivos = this.notaFiscalService.listarNotaFiscal(pageable);

        Mockito.verify(this.notaFiscalRepository, Mockito.times(1)).findAllNotasAtivas(pageable);
        Assertions.assertNotNull(notaProjectionAtivos);
    }

    @Test
    public void consultarNotaFiscal() {

        NotaFiscal notaFiscal = new NotaFiscal();
        notaFiscal.setIdNotaFiscal(1L);

        Optional<NotaFiscal> optionalNotaFiscal = Optional.of(notaFiscal);
        optionalNotaFiscal.get().setFornecedor(new Fornecedor());

        NotaFiscalDto notaFiscalDto = new NotaFiscalDto();

        Mockito.when(this.modelMapper.map(optionalNotaFiscal, NotaFiscalDto.class)).thenReturn(notaFiscalDto);
        Mockito.when(notaFiscalRepository.findById(1L)).thenReturn(optionalNotaFiscal);

        NotaFiscalDto notaFiscalDtoFound =  notaFiscalService.consultarNotaFiscal(1L);

        Assertions.assertNotNull(notaFiscalDtoFound);
    }

    @Test
    public void consultarNumeroDocumentoFiscal() {
        List<NotaFiscalDto> list = this.notaFiscalService.findByNumeroDocumentoFiscal("anyNumber");
        Assertions.assertNotNull(list);
    }

    @Test
    public void removerNotaFiscal() {
        Assertions.assertDoesNotThrow(() -> this.notaFiscalService.removerNotaFiscal(1L));
    }
}
