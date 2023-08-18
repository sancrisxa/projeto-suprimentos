package br.com.tjro.supribackend.service;

import br.com.tjro.supribackend.dto.ItemNotaFiscalDto;
import br.com.tjro.supribackend.model.ItemNotaFiscal;
import br.com.tjro.supribackend.model.NotaFiscal;
import br.com.tjro.supribackend.repository.ItemNotaFiscalRepository;
import br.com.tjro.supribackend.repository.NotaFiscalRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ItemNotaFiscalServiceTest {

    @Mock
    ItemNotaFiscalDto notaFiscalDto;

    @Mock
    ItemNotaFiscal itemNotaFiscal;
    @Mock
    ItemNotaFiscalRepository itemNotaFiscalRepository;
    @Mock
    NotaFiscalRepository notaFiscalRepository;
    @InjectMocks
    ItemNotaFiscalServiceImpl itemNotaFiscalService;
    @Mock
    NotaFiscal notaFiscal;

    @Test
    public void salvarEditarItemNotaFiscal() {
        ItemNotaFiscalDto itemNotaFiscalDto = new ItemNotaFiscalDto();
        itemNotaFiscalDto.setDescricao("teste");
        ItemNotaFiscalDto itemNotaFiscalSaved = itemNotaFiscalService.salvarEditarItemNotaFiscal(itemNotaFiscalDto, null);

        Assertions.assertEquals("teste", itemNotaFiscalSaved.getDescricao());
    }

    @Test
    public void salvarEditarItemNotaFiscalComId() {
        ItemNotaFiscalDto itemNotaFiscalDto = new ItemNotaFiscalDto();
        itemNotaFiscalDto.setDescricao("teste");
        ItemNotaFiscalDto itemNotaFiscalSaved = itemNotaFiscalService.salvarEditarItemNotaFiscal(itemNotaFiscalDto, 1L);

        Assertions.assertEquals("teste", itemNotaFiscalSaved.getDescricao());
    }

    @Test
    public void buscarItemNotaFiscal() {

        Mockito.when(itemNotaFiscal.getNotaFiscal()).thenReturn(notaFiscal);
        Mockito.when(itemNotaFiscalRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(itemNotaFiscal));
        ItemNotaFiscalDto itemNotaFiscalDto = itemNotaFiscalService.buscarItemNotaFiscal(1L);
        Assertions.assertNotNull(itemNotaFiscalDto);
    }

    @Test
    public void removerItemNotaFiscal() {
        Mockito.when(itemNotaFiscal.getNotaFiscal()).thenReturn(notaFiscal);
        Mockito.when(itemNotaFiscalRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(itemNotaFiscal));
        Assertions.assertDoesNotThrow(() ->itemNotaFiscalService.removerItemNotaFiscal(1L));
    }

    @Test
    public void findAllItemsNotaFiscal() {
        List<ItemNotaFiscalDto> list = this.itemNotaFiscalService.findAllItemsNotaFiscal(1L);
        Assertions.assertNotNull(list);
    }

    @Test
    public void removerItemNotaFiscalByIdNotaFiscal() {

        Assertions.assertDoesNotThrow(() -> this.itemNotaFiscalService.removerItemNotaFiscalByIdNotaFiscal(1L));
    }
}
