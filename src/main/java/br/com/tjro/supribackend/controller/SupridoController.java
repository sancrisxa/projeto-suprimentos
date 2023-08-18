package br.com.tjro.supribackend.controller;

import br.com.tjro.supribackend.dto.SupridoDto;
import br.com.tjro.supribackend.service.SupridoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/suprido")
public class SupridoController {

    @Autowired
    private final SupridoService supridoService;

    @GetMapping
    public ResponseEntity<Page<SupridoDto>> listarSuprido(Pageable pageable) {
        Page<SupridoDto> supridoDtos = this.supridoService.listarSuprido(pageable);
        return ResponseEntity.ok(supridoDtos);
    }

    @GetMapping("/{matricula}")
    public ResponseEntity<SupridoDto> buscarSupridoMatricula(@PathVariable String matricula) {
        SupridoDto supridoDto = this.supridoService.buscarSupridoMatricula(matricula);
        return ResponseEntity.ok(supridoDto);
    }
}
