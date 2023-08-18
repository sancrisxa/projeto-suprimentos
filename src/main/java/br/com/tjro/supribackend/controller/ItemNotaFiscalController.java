package br.com.tjro.supribackend.controller;

import br.com.tjro.supribackend.dto.ItemNotaFiscalDto;
import br.com.tjro.supribackend.dto.ResponseInsertDto;
import br.com.tjro.supribackend.service.ItemNotaFiscalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item-nota-fiscal")
public class ItemNotaFiscalController {

    @Autowired
    ItemNotaFiscalService itemNotaFiscalService;

    @PostMapping
    public ResponseEntity<ResponseInsertDto> salvarItemNotaFiscal(@Valid @RequestBody ItemNotaFiscalDto itemNotaFiscalDto) throws Exception {

        ItemNotaFiscalDto savedItemNotaFiscalDto = itemNotaFiscalService.salvarEditarItemNotaFiscal(itemNotaFiscalDto, null);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseInsertDto(savedItemNotaFiscalDto != null ? savedItemNotaFiscalDto.getIdItemNotaFiscal() : null));
    }

    @PutMapping("/{id}")
    public ResponseEntity editarItemNotaFiscal(@Valid @RequestBody ItemNotaFiscalDto itemNotaFiscalDto, @PathVariable(value = "id") Long id) {
        itemNotaFiscalService.salvarEditarItemNotaFiscal(itemNotaFiscalDto, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemNotaFiscalDto> buscarItemNotaFiscal(@PathVariable(value = "id") Long id) {
        ItemNotaFiscalDto itemNotaFiscalDto = itemNotaFiscalService.buscarItemNotaFiscal(id);
        return ResponseEntity.ok(itemNotaFiscalDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removerItemNotaFiscal(@PathVariable(value = "id") Long id) {
        itemNotaFiscalService.removerItemNotaFiscal(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/lista/{id}")
    public ResponseEntity<List<ItemNotaFiscalDto>> listarItensNotasFiscais(@PathVariable(value = "id") Long id) {
        List<ItemNotaFiscalDto> itemNotasFiscais = itemNotaFiscalService.findAllItemsNotaFiscal(id);
        return ResponseEntity.ok(itemNotasFiscais);
    }
}
