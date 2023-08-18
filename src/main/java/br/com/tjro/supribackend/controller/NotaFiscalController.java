package br.com.tjro.supribackend.controller;

import br.com.tjro.supribackend.dto.NotaFiscalDto;
import br.com.tjro.supribackend.dto.ResponseInsertDto;
import br.com.tjro.supribackend.projection.NotaProjectionAtivo;
import br.com.tjro.supribackend.service.NotaFiscalServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/nota-fiscal")
public class NotaFiscalController {

    private final NotaFiscalServiceImpl notaFiscalService;

    @PostMapping
    public ResponseEntity<ResponseInsertDto> salvarNotaFiscal(@Valid @RequestBody NotaFiscalDto notaFiscalDto) {

        NotaFiscalDto save = notaFiscalService.salvarEditarNotaFiscal(notaFiscalDto, null);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseInsertDto(save != null ? save.getIdNotaFiscal() : null));
    }

    @GetMapping
    public ResponseEntity<Page<NotaProjectionAtivo>> listarNotaFiscal(Pageable pageable) {
        Page<NotaProjectionAtivo> notasFiscais = notaFiscalService.listarNotaFiscal(pageable);
        return ResponseEntity.ok(notasFiscais);
    }

    @PutMapping("/{idNotaFiscal}")
    public ResponseEntity editarNotaFiscal(@Valid @RequestBody NotaFiscalDto notaFiscalDto, @PathVariable(value = "idNotaFiscal") Long idNotaFiscal) {
        notaFiscalService.salvarEditarNotaFiscal(notaFiscalDto, idNotaFiscal);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{idNotaFiscal}")
    public ResponseEntity<NotaFiscalDto> consultarNotaFiscal(@PathVariable(value = "idNotaFiscal") Long idNotaFiscal) {
        NotaFiscalDto notaFiscalDto = notaFiscalService.consultarNotaFiscal(idNotaFiscal);
        return ResponseEntity.ok(notaFiscalDto);
    }
}
