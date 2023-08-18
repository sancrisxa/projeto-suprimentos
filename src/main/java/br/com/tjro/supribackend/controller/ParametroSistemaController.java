package br.com.tjro.supribackend.controller;

import br.com.tjro.supribackend.dto.ParametroSistemaDto;
import br.com.tjro.supribackend.dto.ResponseInsertDto;
import br.com.tjro.supribackend.service.ParametroSistemaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/parametro-sistema")
public class ParametroSistemaController {

    @Autowired
    private final ParametroSistemaService parametroSistemaService;

    @PostMapping
    public ResponseEntity<ResponseInsertDto> salvarParametroSistema(@Valid @RequestBody ParametroSistemaDto parametroSistemaDto) {
        ParametroSistemaDto save = parametroSistemaService.salvarEditarParametroSistema(parametroSistemaDto, null);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseInsertDto(save != null ? save.getIdParametroSistema() : null));
    }

    @PutMapping("/{id}")
    public ResponseEntity editarParametroSistema(@Valid @RequestBody ParametroSistemaDto parametroSistemaDto, @PathVariable(value = "id") Long id) {
        parametroSistemaService.salvarEditarParametroSistema(parametroSistemaDto, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<ParametroSistemaDto>> listarParametroSistema() {
        List<ParametroSistemaDto> parametroSistemaDtos = this.parametroSistemaService.listarParametroSistema();
        return ResponseEntity.ok(parametroSistemaDtos);
    }
}
