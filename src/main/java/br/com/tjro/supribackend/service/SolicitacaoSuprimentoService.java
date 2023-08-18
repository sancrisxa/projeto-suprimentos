package br.com.tjro.supribackend.service;

import br.com.tjro.supribackend.dto.SolicitacaoSuprimentoDto;
import br.com.tjro.supribackend.dto.SupridoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SolicitacaoSuprimentoService {
    SolicitacaoSuprimentoDto salvarEditarSolicitacaoSuprimento(SolicitacaoSuprimentoDto solicitacaoSuprimentoDto, Long idSolicitacaoSuprimento);
    SolicitacaoSuprimentoDto findById(Long idSolicitacaoSuprimento);
    Page<SolicitacaoSuprimentoDto> listarSuprimentoSolicitacao(Pageable pageable);
    void removerSolicitacao(Long idSolicitacao);
    List<SolicitacaoSuprimentoDto> findSolicitacaoSuprimentoBySuprido(String matricula);
}
