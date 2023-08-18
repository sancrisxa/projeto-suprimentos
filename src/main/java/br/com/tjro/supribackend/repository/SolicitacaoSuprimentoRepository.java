package br.com.tjro.supribackend.repository;

import br.com.tjro.supribackend.dto.SolicitacaoSuprimentoDto;
import br.com.tjro.supribackend.model.SolicitacaoSuprimento;
import br.com.tjro.supribackend.model.Suprido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitacaoSuprimentoRepository extends JpaRepository<SolicitacaoSuprimento, Long> {

    @Query("from SolicitacaoSuprimento where matriculaSuprido = ?1")
    List<SolicitacaoSuprimento> findSolicitacaoSuprimentoBySuprido(String matricula);
}
