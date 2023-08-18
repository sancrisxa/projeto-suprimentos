package br.com.tjro.supribackend.controller;

import br.com.tjro.supribackend.dto.ElementoDespesaDto;
import br.com.tjro.supribackend.dto.ResponseInsertDto;
import br.com.tjro.supribackend.service.ElementoDespesaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/elemento-despesa")
public class ElementoDespesaController {

    private final ElementoDespesaService elementoDespesaService;

    @GetMapping("/{idSolicitacao}")
    public ResponseEntity<Page<ElementoDespesaDto>> listarElementoDespesa(Pageable pageable, @PathVariable Long idSolicitacao) {
        Page<ElementoDespesaDto> elementoDespesaDtos = this.elementoDespesaService.listarElementoDespesa(pageable, idSolicitacao);

        return ResponseEntity.ok(elementoDespesaDtos);
    }

    @GetMapping("/elemento/{idElementoDespesa}")
    public ResponseEntity<ElementoDespesaDto> findByIdElementoDespesa(@PathVariable Long idElementoDespesa) {
        ElementoDespesaDto elementoDespesaDto = this.elementoDespesaService.findById(idElementoDespesa);

        return ResponseEntity.ok(elementoDespesaDto);
    }

    @PostMapping("/{idSolicitacao}")
    public ResponseEntity<ResponseInsertDto> salvarElementoDespesa(@Valid @RequestBody ElementoDespesaDto elementoDespesaDto, @PathVariable Long idSolicitacao) throws Exception {

        ElementoDespesaDto save = this.elementoDespesaService.salvarEditarElementoDespesaUnico(elementoDespesaDto, idSolicitacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseInsertDto(save != null ? save.getIdElementoDespesa() : null));
    }

    @PutMapping("/{idElementoDespesa}")
    public ResponseEntity<ResponseInsertDto> editarSolicitacaoSuprimento(@Valid @RequestBody ElementoDespesaDto elementoDespesaDto, @PathVariable Long idElementoDespesa) {
        ElementoDespesaDto elementoDespesaDtoSaved = this.elementoDespesaService.salvarEditarElementoDespesaUnico(elementoDespesaDto, idElementoDespesa);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{idElementoDespesa}")
    public ResponseEntity removerItemNotaFiscal(@PathVariable(value = "idElementoDespesa") Long idElementoDespesa) {
        this.elementoDespesaService.removerElementoDespesa(idElementoDespesa);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
