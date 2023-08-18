package br.com.tjro.supribackend.service;

import br.com.tjro.supribackend.dto.NotaComItemDto;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface ApiService {
    List<NotaComItemDto> buscarNotaPorData(Pageable pageable, LocalDate dataInicial, LocalDate dataFinal);
}
