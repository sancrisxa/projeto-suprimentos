package br.com.tjro.supribackend.service;

import br.com.tjro.supribackend.dto.SupridoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SupridoService {

    Page<SupridoDto> listarSuprido(Pageable page);
    SupridoDto buscarSupridoMatricula(String matricula);
}
