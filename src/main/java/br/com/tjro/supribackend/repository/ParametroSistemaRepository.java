package br.com.tjro.supribackend.repository;

import br.com.tjro.supribackend.model.ParametroSistema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParametroSistemaRepository extends JpaRepository<ParametroSistema, Long> {
}
