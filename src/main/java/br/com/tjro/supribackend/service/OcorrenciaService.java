package br.com.tjro.supribackend.service;

import br.com.tjro.supribackend.dto.OcorrenciaDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OcorrenciaService {
    OcorrenciaDto salvarOcorrencia(OcorrenciaDto ocorrenciaDto);

    Page<OcorrenciaDto> listarOcorrencias(Pageable pageable, Long idFornecedir);

    OcorrenciaDto editarOcorrencia(OcorrenciaDto ocorrenciaDto, Long idOcorrencia);

    void excluirOcorrencia(Long idOcorrencia);

    OcorrenciaDto findByIdOcorrencia(Long idOcorrencia);
}
