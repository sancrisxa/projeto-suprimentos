package br.com.tjro.supribackend.service;

import br.com.tjro.supribackend.dto.NotaComItemDto;
import br.com.tjro.supribackend.model.Fornecedor;
import br.com.tjro.supribackend.model.NotaFiscal;
import br.com.tjro.supribackend.projection.NotaProjection;
import br.com.tjro.supribackend.repository.NotaFiscalRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class ApiServiceTest {

    @Mock
    NotaFiscalRepository notaFiscalRepository;
    @Mock
    ItemNotaFiscalService itemNotaFiscalService;
    @Mock
    Pageable pageable;

    @Mock
    NotaProjection notaProjection;

    @InjectMocks
    ApiServiceImpl apiService;

    @Test
    public void buscarNotaPorData() {


        List<NotaProjection> notaProjectionList = new ArrayList<>();
        notaProjectionList.add(notaProjection);

        List<NotaFiscal> notas = new ArrayList<>();
        NotaFiscal notaFiscal = new NotaFiscal();
        Fornecedor fornecedor = new Fornecedor();
        notaFiscal.setFornecedor(fornecedor);
        notas.add(notaFiscal);

        Mockito.when(this.notaFiscalRepository.buscarNotaPorData(pageable, LocalDate.now(), LocalDate.now())).thenReturn(new PageImpl<>(notaProjectionList));
        List<NotaComItemDto> notaComItemDtos = this.apiService.buscarNotaPorData(pageable, LocalDate.now(), LocalDate.now());

        Mockito.verify(this.notaFiscalRepository, Mockito.times(1)).buscarNotaPorData(pageable, LocalDate.now(), LocalDate.now());
        Assertions.assertNotNull(notaComItemDtos);
    }

}
