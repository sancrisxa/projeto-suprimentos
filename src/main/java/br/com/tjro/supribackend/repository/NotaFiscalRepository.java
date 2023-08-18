package br.com.tjro.supribackend.repository;

import br.com.tjro.supribackend.model.NotaFiscal;
import br.com.tjro.supribackend.projection.NotaApiPublicProjection;
import br.com.tjro.supribackend.projection.NotaProjection;
import br.com.tjro.supribackend.projection.NotaProjectionAtivo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NotaFiscalRepository extends JpaRepository<NotaFiscal, Long> {

    @Query("from NotaFiscal where numeroDocumentoFiscal = ?1")
    List<NotaFiscal> findByNumeroDocumentoFiscal(String numeroDocumentoFiscal);

    @Query(name = "sql.lista.notas", nativeQuery = true)
    Page<NotaProjection> buscarNotaPorData(Pageable pageable,
                                           @Param("dataInicial") LocalDate dataInicial,
                                           @Param("dataFinal") LocalDate dataFinal);

    @Query(name = "sql.lista.notas-ano-mes", nativeQuery = true)
    Page<NotaApiPublicProjection> buscarNotaPorAnoMes(Pageable pageable,
                                                      @Param("ano") String ano,
                                                      @Param("mes") String mes);

    @Query(name = "sql.lista.notasAtivas", nativeQuery = true)
    Page<NotaProjectionAtivo> findAllNotasAtivas(Pageable pageable);
}
