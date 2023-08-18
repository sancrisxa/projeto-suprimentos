package br.com.tjro.supribackend.service;

import br.com.tjro.supribackend.Exceptions.CpfCnpjDuplicateException;
import br.com.tjro.supribackend.Exceptions.CpfCnpjInvalidoException;
import br.com.tjro.supribackend.Exceptions.FornecedorNotFoundException;
import br.com.tjro.supribackend.dto.FornecedorDto;
import br.com.tjro.supribackend.model.Fornecedor;
import br.com.tjro.supribackend.projection.FornecedorProjection;
import br.com.tjro.supribackend.repository.FornecedorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class FornecedorServiceTest {

    @Mock
    FornecedorProjection fornecedorProjection;

    @Mock
    private FornecedorRepository fornecedorRepository;
    @Mock
    private ModelMapper mapper;
    @InjectMocks
    private FornecedorServiceImpl fornecedorService;

    @Test
    public void consultarFornecedor() {

        Fornecedor fornecedor = new Fornecedor();

        Mockito.when(fornecedorRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(fornecedor));
        fornecedorService.consultarFornecedor(1L);
    }

    @Test
    public void salvarEditarFornecedor() throws Exception {

        FornecedorDto fornecedorDto = new FornecedorDto();
        fornecedorDto.setCpfCnpj("1234567891011");

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setCpfCnpj("1234567891011");

        List<Fornecedor> fornecedorList = new ArrayList<>();

        Mockito.when(mapper.map(fornecedorDto, Fornecedor.class)).thenReturn(fornecedor);
        Mockito.when(this.fornecedorRepository.findByCpfCnpj(fornecedor.getCpfCnpj())).thenReturn(fornecedorList);
        Mockito.when(this.fornecedorRepository.save(fornecedor)).thenReturn(fornecedor);

        this.fornecedorService.salvarEditarFornecedor(fornecedorDto, null);

        Mockito.verify(this.fornecedorRepository, Mockito.times(1)).save(fornecedor);
        Mockito.verify(this.fornecedorRepository, Mockito.times(1)).findByCpfCnpj("1234567891011");
    }
    @Test
    public void salvarEditarFornecedorComId() throws Exception {

        FornecedorDto fornecedorDto = new FornecedorDto();
        fornecedorDto.setCpfCnpj("1234567891011");

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setCpfCnpj("1234567891011");

        List<Fornecedor> fornecedorList = new ArrayList<>();
        fornecedorList.add(fornecedor);

        Mockito.when(mapper.map(fornecedorDto, Fornecedor.class)).thenReturn(fornecedor);
        Mockito.when(this.fornecedorRepository.findByCpfCnpj(fornecedor.getCpfCnpj())).thenReturn(fornecedorList);
        Mockito.when(this.fornecedorRepository.save(fornecedor)).thenReturn(fornecedor);

        this.fornecedorService.salvarEditarFornecedor(fornecedorDto, 1L);

        Mockito.verify(this.fornecedorRepository, Mockito.times(1)).save(fornecedor);
        Mockito.verify(this.fornecedorRepository, Mockito.times(1)).findByCpfCnpj("1234567891011");
    }

    @Test
    public void findFornecedores() {

        Page<FornecedorProjection> page = null;
        Mockito.when(fornecedorRepository.findFornecedores(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(page);

        fornecedorService.findFornecedores(null, "123456", "123456");
    }

    @Test
    public void findByCpfCnpj() {

        List<FornecedorProjection> fornecedorProjectionList = new ArrayList<>();
        fornecedorProjectionList.add(fornecedorProjection);
        Mockito.when(this.fornecedorRepository.findAllByCpfCnpj(Mockito.anyString())).thenReturn(fornecedorProjectionList);

        List<FornecedorProjection> findByCpfCnpj = this.fornecedorService.findByCpfCnpj(Mockito.anyString());

        Assertions.assertNotNull(findByCpfCnpj);
    }

    @Test
    public void salvarEditarFornecedorCpfCnpjInvalidoException() {
        Assertions.assertThrows(CpfCnpjInvalidoException.class, () -> {
            //Code under test
            FornecedorDto fornecedorDto = new FornecedorDto();
            fornecedorDto.setCpfCnpj("123");
            this.fornecedorService.salvarEditarFornecedor(fornecedorDto, 1L);
        });
    }

    @Test
    public void salvarEditarFornecedorCpfCnpjDuplicateException() {
        Assertions.assertThrows(CpfCnpjDuplicateException.class, () -> {
            //Code under test
            FornecedorDto fornecedorDto = new FornecedorDto();
            fornecedorDto.setCpfCnpj("1234567891011");
            Fornecedor fornecedor = new Fornecedor();
            fornecedor.setCpfCnpj("1234567891011");
            List<Fornecedor> fornecedorList = new ArrayList<>();
            fornecedorList.add(fornecedor);
            Mockito.when(mapper.map(fornecedorDto, Fornecedor.class)).thenReturn(fornecedor);
            Mockito.when(this.fornecedorRepository.findByCpfCnpj("1234567891011")).thenReturn(fornecedorList);
            this.fornecedorService.salvarEditarFornecedor(fornecedorDto, null);
        });
    }

    @Test
    public void findByCpfCnpjCpFornecedorNotFoundException() {
        Assertions.assertThrows(FornecedorNotFoundException.class, () -> {
            //Code under test
            this.fornecedorService.findByCpfCnpj("1234567891011");
        });
    }

    @Test
    public void consultarFornecedorFornecedorNotFoundException() {
        Assertions.assertThrows(FornecedorNotFoundException.class, () -> {
            //Code under test
            this.fornecedorService.consultarFornecedor(1L);
        });
    }
}
