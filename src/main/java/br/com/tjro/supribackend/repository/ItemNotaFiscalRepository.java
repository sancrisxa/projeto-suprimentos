package br.com.tjro.supribackend.repository;

import br.com.tjro.supribackend.model.ItemNotaFiscal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemNotaFiscalRepository extends JpaRepository<ItemNotaFiscal, Long> {
    @Query("from ItemNotaFiscal where notaFiscal.idNotaFiscal = ?1")
    List<ItemNotaFiscal> findAll(Long idNotaFiscal);

    @Modifying
    @Query("delete from ItemNotaFiscal where notaFiscal.idNotaFiscal = ?1")
    void deleteItensNotasFiscaisByIdNotaFiscal(Long idNotaFiscal);

}
