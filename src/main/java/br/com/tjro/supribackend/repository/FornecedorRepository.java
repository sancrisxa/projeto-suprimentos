package br.com.tjro.supribackend.repository;

import br.com.tjro.supribackend.model.Fornecedor;
import br.com.tjro.supribackend.projection.FornecedorProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
    @Query(name = "sql.lista.fornecedores", nativeQuery = true)
    Page<FornecedorProjection>findFornecedores(Pageable pageable,
                                                @Param("razaoSocial") String razaoSocial,
                                                @Param("cpfCnpj") String cpfCnpj
    );

    @Query(name = "sql.lista.consultaCpfCnpj", nativeQuery = true)
    List<FornecedorProjection> findAllByCpfCnpj(@Param("cpfCnpj") String cpfCnpj);

   List<Fornecedor> findByCpfCnpj(String cpfCnpj);
}
