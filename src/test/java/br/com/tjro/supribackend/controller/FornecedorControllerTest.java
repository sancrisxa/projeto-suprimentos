package br.com.tjro.supribackend.controller;

import br.com.tjro.supribackend.dto.FornecedorDto;
import br.com.tjro.supribackend.dto.ResponseInsertDto;
import br.com.tjro.supribackend.projection.FornecedorProjection;
import br.com.tjro.supribackend.service.FornecedorServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class FornecedorControllerTest {

    @Mock
    Pageable pageable;
    @Mock
    private FornecedorServiceImpl fornecedorService;
    @InjectMocks
    private FornecedorController fornecedorController;

    @Test
    void salvarFornecedor() throws Exception {

        FornecedorDto fornecedorDto = new FornecedorDto();
        ResponseEntity<ResponseInsertDto> responseEntity = this.fornecedorController.salvarFornecedor(fornecedorDto);

        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void editarFornecedor() throws Exception {
        FornecedorDto fornecedorDto = new FornecedorDto();
        ResponseEntity<ResponseInsertDto> responseEntity = this.fornecedorController.editarFornecedor(fornecedorDto, 1L);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void consultarFornecedor() {
        ResponseEntity<FornecedorDto> responseEntity = this.fornecedorController.consultarFornecedor(1l);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void pesquisarFornecedores() {
        ResponseEntity<Page<FornecedorProjection>> responseEntity = this.fornecedorController.pesquisarFornecedor(pageable, "razao-social", "1234567891011");

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void consultarCpfCnpj() {
        ResponseEntity<List<FornecedorProjection>> responseEntity = this.fornecedorController.consultarCpfCnpj("1234567891011");

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
