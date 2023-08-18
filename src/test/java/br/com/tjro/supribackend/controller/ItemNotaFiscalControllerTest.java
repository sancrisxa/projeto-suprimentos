package br.com.tjro.supribackend.controller;

import br.com.tjro.supribackend.dto.ItemNotaFiscalDto;
import br.com.tjro.supribackend.dto.ResponseInsertDto;
import br.com.tjro.supribackend.service.ItemNotaFiscalService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
public class ItemNotaFiscalControllerTest {
    @Mock
    ItemNotaFiscalService itemNotaFiscalService;
    @InjectMocks
    ItemNotaFiscalController itemNotaFiscalController;
    @Test
    public void salvarItemNotaFiscal() throws Exception {
        ItemNotaFiscalDto itemNotaFiscalDto = new ItemNotaFiscalDto();
        ResponseEntity<ResponseInsertDto> responseEntity = this.itemNotaFiscalController.salvarItemNotaFiscal(itemNotaFiscalDto);

        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void editarItemNotaFiscal() {
        ItemNotaFiscalDto itemNotaFiscalDto = new ItemNotaFiscalDto();
        ResponseEntity<ResponseInsertDto> responseEntity = this.itemNotaFiscalController.editarItemNotaFiscal(itemNotaFiscalDto, 1L);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void buscarItemNotaFiscal() {
        assertDoesNotThrow(() -> itemNotaFiscalService.buscarItemNotaFiscal(1L));
        assertDoesNotThrow(() -> itemNotaFiscalController.buscarItemNotaFiscal(1L));
    }

    @Test
    public void removerItemNotaFiscal() {
        assertDoesNotThrow(() -> itemNotaFiscalService.removerItemNotaFiscal(1L));
        assertDoesNotThrow(() -> itemNotaFiscalController.removerItemNotaFiscal(1L));
    }

    @Test
    public void listarItensNotasFiscais() {
        assertDoesNotThrow(() -> itemNotaFiscalService.findAllItemsNotaFiscal(1L));
        assertDoesNotThrow(() -> itemNotaFiscalController.listarItensNotasFiscais(1L));
    }
}
