package br.com.tjro.supribackend.controller;

import br.com.tjro.supribackend.dto.OcorrenciaDto;
import br.com.tjro.supribackend.dto.ResponseInsertDto;
import br.com.tjro.supribackend.service.OcorrenciaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/ocorrencias")
public class OcorrenciaController {

    @Autowired
    private OcorrenciaService ocorrenciaService;

    @PostMapping
    public ResponseEntity<ResponseInsertDto> cadastrarOcorrencia(@Valid @RequestBody OcorrenciaDto ocorrenciaDto) {
        OcorrenciaDto ocorrenciaDtoCadastrada = this.ocorrenciaService.salvarOcorrencia(ocorrenciaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseInsertDto(ocorrenciaDtoCadastrada.getIdOcorrencia()));
    }

    @GetMapping("/fornecedor/{idFornecedor}")
    public ResponseEntity<Page<OcorrenciaDto>> listarOcorrencias(Pageable pageable, @PathVariable Long idFornecedor) {
        Page<OcorrenciaDto> ocorrenciaDtos = this.ocorrenciaService.listarOcorrencias(pageable, idFornecedor);
        return ResponseEntity.ok(ocorrenciaDtos);
    }

    @PutMapping("/{idOcorrencia}")
    public ResponseEntity<OcorrenciaDto> editarOcorrencia(@Valid @RequestBody OcorrenciaDto ocorrenciaDto,@PathVariable Long idOcorrencia) {
        OcorrenciaDto ocorrenciaDtoCadastrada = this.ocorrenciaService.editarOcorrencia(ocorrenciaDto, idOcorrencia);
        return ResponseEntity.ok(ocorrenciaDto);
    }

    @DeleteMapping("/{idOcorrencia}")
    public ResponseEntity excluirOcorrencia(@PathVariable(value = "idOcorrencia") Long idOcorrencia) {
        this.ocorrenciaService.excluirOcorrencia(idOcorrencia);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{idOcorrencia}")
    public ResponseEntity<OcorrenciaDto> findByIdOcorrencia(@PathVariable(value = "idOcorrencia") Long idOcorrencia) {
        OcorrenciaDto ocorrenciaDto = this.ocorrenciaService.findByIdOcorrencia(idOcorrencia);
        return ResponseEntity.ok(ocorrenciaDto);
    }
}
