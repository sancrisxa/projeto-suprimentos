package br.com.tjro.supribackend.service;

import br.com.tjro.supribackend.dto.ParametroSistemaDto;

import java.util.List;

public interface ParametroSistemaService {
    ParametroSistemaDto salvarEditarParametroSistema(ParametroSistemaDto parametroSistemaDto, Long idParametroSistema);
    List<ParametroSistemaDto> listarParametroSistema();
}
