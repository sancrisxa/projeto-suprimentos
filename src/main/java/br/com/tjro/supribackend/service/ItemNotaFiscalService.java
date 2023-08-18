package br.com.tjro.supribackend.service;

import br.com.tjro.supribackend.dto.ItemNotaFiscalDto;

import java.util.List;

public interface ItemNotaFiscalService {
    ItemNotaFiscalDto salvarEditarItemNotaFiscal(ItemNotaFiscalDto itemNotaFiscalDto, Long idItemNotaFiscal);
    ItemNotaFiscalDto buscarItemNotaFiscal(Long id);
    void removerItemNotaFiscal(Long id);
    List<ItemNotaFiscalDto> findAllItemsNotaFiscal(Long id);
    void removerItemNotaFiscalByIdNotaFiscal(Long idNotaFiscal);
}
