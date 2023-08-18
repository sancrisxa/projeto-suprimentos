package br.com.tjro.supribackend.repository;

import br.com.tjro.supribackend.model.Suprido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupridoRepository  extends JpaRepository<Suprido, Long> {
    @Query(value = "SELECT MATRICULA AS matricula, FUNCIONAL AS funcional, NME_SERVIDOR AS nome_servidor, CDG_LOTACAO AS codigo_lotacao, NME_LOTACAO AS nome_lotacao, CDG_UNIDADE_SUPRIMENTO AS codigo_unidade_suprimento, NME_UNIDADE_SUPRIMENTO AS nome_unidade_suprimento, CDG_COMARCA AS codigo_comarca, NME_COMARCAR AS nome_comarca\n" +
            "FROM GEGESP.VW_SPS_RELACAO_SUPRIDOS@ORASRH WHERE matricula = ?1", nativeQuery = true)
    Optional<Suprido> findByMatricula(String matricula);

    @Query(value = "SELECT MATRICULA AS matricula, FUNCIONAL AS funcional, NME_SERVIDOR AS nome_servidor, CDG_LOTACAO AS codigo_lotacao, NME_LOTACAO AS nome_lotacao, CDG_UNIDADE_SUPRIMENTO AS codigo_unidade_suprimento, NME_UNIDADE_SUPRIMENTO AS nome_unidade_suprimento, CDG_COMARCA AS codigo_comarca, NME_COMARCAR AS nome_comarca\n" +
            "FROM GEGESP.VW_SPS_RELACAO_SUPRIDOS@ORASRH ORDER BY nome_servidor\n" +
            "\n", nativeQuery = true)
    Page<Suprido> findAllBaseEgesp(Pageable pageable);
}
