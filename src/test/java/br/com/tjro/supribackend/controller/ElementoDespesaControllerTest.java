package br.com.tjro.supribackend.controller;

import br.com.tjro.supribackend.dto.ElementoDespesaDto;
import br.com.tjro.supribackend.service.ElementoDespesaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class ElementoDespesaControllerTest {

    @InjectMocks
    ElementoDespesaController elementoDespesaController;

    @Mock
    ElementoDespesaService elementoDespesaService;

    @Mock
    Pageable pageable;

    @Test
    void listarElementoDespesa() {
        this.elementoDespesaController.listarElementoDespesa(pageable, 1L);
    }

    @Test
    void findByIdElementoDespesa() {
        this.elementoDespesaController.findByIdElementoDespesa(1L);
    }

    @Test
    void salvarElementoDespesa() throws Exception {
        ElementoDespesaDto elementoDespesaDto = new ElementoDespesaDto();
        this.elementoDespesaController.salvarElementoDespesa(elementoDespesaDto, null);
    }

    @Test
    void editarSolicitacaoSuprimento() throws Exception {
        ElementoDespesaDto elementoDespesaDto = new ElementoDespesaDto();
        this.elementoDespesaController.salvarElementoDespesa(elementoDespesaDto, 1L);
    }

    @Test
    void removerItemNotaFiscal() {
        this.elementoDespesaController.removerItemNotaFiscal(1L);
    }
}