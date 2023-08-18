package br.com.tjro.supribackend.controller;

import br.com.tjro.supribackend.dto.ElementoDespesaDto;
import br.com.tjro.supribackend.dto.ResponseInsertDto;
import br.com.tjro.supribackend.dto.SolicitacaoSuprimentoDto;
import br.com.tjro.supribackend.dto.AutorizaDto;
import br.com.tjro.supribackend.enums.StatusSolicitacaoSuprimento;
import br.com.tjro.supribackend.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/solicitacao-suprimento")
public class SolicitacaoSuprimentoController {

    private final SolicitacaoSuprimentoService solicitacaoSuprimentoService;

    private final ElementoDespesaService elementoDespesaService;

    private final SupridoService supridoService;

    private final ParametroSistemaService parametroSistemaService;

    @PostMapping
    public ResponseEntity<ResponseInsertDto> salvarSolicitacaoSuprimento(@Valid @RequestBody SolicitacaoSuprimentoDto solicitacaoSuprimentoDto) {

        if (solicitacaoSuprimentoDto.getValorTotal().compareTo(this.parametroSistemaService.listarParametroSistema().get(0).getTetoSuprimento()) == 1) {
            solicitacaoSuprimentoDto.setStatusSolicitacaoSuprimento(StatusSolicitacaoSuprimento.EM_APROVACAO);
        }else if (solicitacaoSuprimentoDto.getStatusSolicitacaoSuprimento() == null) {
            solicitacaoSuprimentoDto.setStatusSolicitacaoSuprimento(StatusSolicitacaoSuprimento.SOLICITADO);
        }

        SolicitacaoSuprimentoDto solicitacaoSuprimentoDtoSaved = this.solicitacaoSuprimentoService.salvarEditarSolicitacaoSuprimento(solicitacaoSuprimentoDto, null);

        if (!solicitacaoSuprimentoDto.getElementoDespesaList().isEmpty() && solicitacaoSuprimentoDtoSaved.getIdSolicitacaoSuprimento() != null) {
            this.elementoDespesaService.salvarEditarElementoDespesa(solicitacaoSuprimentoDto.getElementoDespesaList(), solicitacaoSuprimentoDtoSaved);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseInsertDto(solicitacaoSuprimentoDtoSaved.getIdSolicitacaoSuprimento()));
    }

    @PutMapping("/{idSolicitacaoSuprimento}")
    public ResponseEntity<SolicitacaoSuprimentoDto> editarSolicitacaoSuprimento(@Valid @RequestBody SolicitacaoSuprimentoDto solicitacaoSuprimentoDto, @PathVariable Long idSolicitacaoSuprimento) {

        if (solicitacaoSuprimentoDto.getValorTotal().compareTo(this.parametroSistemaService.listarParametroSistema().get(0).getTetoSuprimento()) == 1) {
            solicitacaoSuprimentoDto.setStatusSolicitacaoSuprimento(StatusSolicitacaoSuprimento.EM_APROVACAO);
        }

        SolicitacaoSuprimentoDto solicitacaoSuprimentoDtoSaved = this.solicitacaoSuprimentoService.salvarEditarSolicitacaoSuprimento(solicitacaoSuprimentoDto, idSolicitacaoSuprimento);

        return ResponseEntity.status(HttpStatus.OK).body(solicitacaoSuprimentoDtoSaved);

    }

    @GetMapping
    public ResponseEntity<Page<SolicitacaoSuprimentoDto>>listarSolicitacaoSuprimento(Pageable pageable) {

        Page<SolicitacaoSuprimentoDto> solicitacaoSuprimentosDtos = this.solicitacaoSuprimentoService.listarSuprimentoSolicitacao(pageable);

        return ResponseEntity.ok(solicitacaoSuprimentosDtos);
    }

    @GetMapping("/{idSolicitacaoSuprimento}/elemento-despesa")
    public ResponseEntity<SolicitacaoSuprimentoDto>listarSolicitacaoSuprimentoByIdComElementosDeDespesas(@PathVariable Long idSolicitacaoSuprimento) {

        SolicitacaoSuprimentoDto solicitacaoSuprimentosDtos = this.solicitacaoSuprimentoService.findById(idSolicitacaoSuprimento);

        List<ElementoDespesaDto> elementoDespesaDtos = elementoDespesaService.buscarElementoDespesaByIdSolicitacao(idSolicitacaoSuprimento);

        solicitacaoSuprimentosDtos.setElementoDespesaList(elementoDespesaDtos);
        solicitacaoSuprimentosDtos.setNomeSuprido(this.supridoService.buscarSupridoMatricula(solicitacaoSuprimentosDtos.getMatriculaSuprido()).getNome());

        return ResponseEntity.ok(solicitacaoSuprimentosDtos);
    }

    @PutMapping("/{idSolicitacaoSuprimento}/autorizar/{status}")
    public ResponseEntity<AutorizaDto> autorizarSolicitacao(@Valid @RequestBody AutorizaDto autorizaDto, @PathVariable Long idSolicitacaoSuprimento, @PathVariable String status) {

        SolicitacaoSuprimentoDto solicitacaoSuprimentoDto = this.solicitacaoSuprimentoService.findById(idSolicitacaoSuprimento);

        solicitacaoSuprimentoDto.setStatusSolicitacaoSuprimento(this.getStatus(status));
        solicitacaoSuprimentoDto.setMatriculaUsuario(autorizaDto.getMatriculaUsuario());

        this.solicitacaoSuprimentoService.salvarEditarSolicitacaoSuprimento(solicitacaoSuprimentoDto, idSolicitacaoSuprimento);

        return ResponseEntity.status(HttpStatus.OK).body(autorizaDto);
    }


    @DeleteMapping("/{idSolicitacao}")
    public ResponseEntity removerSolicitacao(@PathVariable(value = "idSolicitacao") Long idSolicitacao) {

        List<ElementoDespesaDto> elementoDespesaDtos = this.elementoDespesaService.buscarElementoDespesaByIdSolicitacao(idSolicitacao);

        elementoDespesaDtos.stream().forEach(elementoDespesaDto -> {
            this.elementoDespesaService.removerElementoDespesa(elementoDespesaDto.getIdElementoDespesa());
        });

        this.solicitacaoSuprimentoService.removerSolicitacao(idSolicitacao);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{idSolicitacaoSuprimento}/gerar-atestado")
    public ResponseEntity<ResponseInsertDto> gerarAtestado(@PathVariable Long idSolicitacaoSuprimento) {

        SolicitacaoSuprimentoDto solicitacaoSuprimentoDto = this.solicitacaoSuprimentoService.findById(idSolicitacaoSuprimento);

        solicitacaoSuprimentoDto.setStatusSolicitacaoSuprimento(StatusSolicitacaoSuprimento.APROVADO);

        this.solicitacaoSuprimentoService.salvarEditarSolicitacaoSuprimento(solicitacaoSuprimentoDto, idSolicitacaoSuprimento);

        return ResponseEntity.ok().build();
    }

    private StatusSolicitacaoSuprimento getStatus(String status) {

        switch (status) {
            case "EM_ANALISE":
                return StatusSolicitacaoSuprimento.EM_ANALISE;
            case "EM_APROVACAO":
                return StatusSolicitacaoSuprimento.EM_APROVACAO;
            case "PRESTACAO":
                return StatusSolicitacaoSuprimento.PRESTACAO;
            case "CADASTRADO":
                return StatusSolicitacaoSuprimento.CADASTRADO;
            case "APROVADO":
                return StatusSolicitacaoSuprimento.APROVADO;
            default:
                return StatusSolicitacaoSuprimento.SOLICITADO;
        }
    }
}
