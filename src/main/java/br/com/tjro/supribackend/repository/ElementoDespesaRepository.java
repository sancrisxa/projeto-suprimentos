package br.com.tjro.supribackend.repository;

import br.com.tjro.supribackend.model.ElementoDespesa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElementoDespesaRepository extends JpaRepository<ElementoDespesa, Long> {

    @Query(value = "SELECT ed FROM ElementoDespesa ed where ed.solicitacaoSuprimento.idSolicitacaoSuprimento = :idSolicitacao")
    Page<ElementoDespesa> findAllBySolicitacao_Id(Pageable pageable, Long idSolicitacao);

    @Query(value = "SELECT ed FROM ElementoDespesa ed where ed.solicitacaoSuprimento.idSolicitacaoSuprimento = :idSolicitacao")
    List<ElementoDespesa> buscarElementoDespesaByIdSolicitacao(Long idSolicitacao);
}
