package br.com.tjro.supribackend.service;

import br.com.tjro.supribackend.dto.ElementoDespesaDto;
import br.com.tjro.supribackend.dto.SolicitacaoSuprimentoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ElementoDespesaService {
    public List<ElementoDespesaDto> salvarEditarElementoDespesa(List<ElementoDespesaDto> elementosDespesaDtos, SolicitacaoSuprimentoDto solicitacaoSuprimentoDto);

    public Page<ElementoDespesaDto> listarElementoDespesa(Pageable pageable, Long idSolicitacao);

    public ElementoDespesaDto salvarEditarElementoDespesaUnico(ElementoDespesaDto elementoDespesaDto, Long idSolicitacao);

    void removerElementoDespesa(Long idElementoDespesa);

    List<ElementoDespesaDto> buscarElementoDespesaByIdSolicitacao(Long idSolicitacao);

    ElementoDespesaDto findById(Long idElementoDespesa);
}
