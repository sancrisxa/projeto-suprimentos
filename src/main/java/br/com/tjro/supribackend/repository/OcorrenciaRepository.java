package br.com.tjro.supribackend.repository;

import br.com.tjro.supribackend.model.Fornecedor;
import br.com.tjro.supribackend.model.Ocorrencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {
    Page<Ocorrencia> findByFornecedor(Pageable pageable, Fornecedor fornecedor);
}
