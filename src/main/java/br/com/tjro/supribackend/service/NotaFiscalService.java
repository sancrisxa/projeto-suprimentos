package br.com.tjro.supribackend.service;

import br.com.tjro.supribackend.dto.NotaFiscalDto;
import br.com.tjro.supribackend.model.NotaFiscal;
import br.com.tjro.supribackend.projection.NotaProjection;
import br.com.tjro.supribackend.projection.NotaProjectionAtivo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface NotaFiscalService {
    public NotaFiscalDto salvarEditarNotaFiscal(NotaFiscalDto notaFiscalDto, Long idNotaFiscal);
    public Page<NotaProjectionAtivo> listarNotaFiscal(Pageable pageable);
    public NotaFiscalDto consultarNotaFiscal(Long id);
    public List<NotaFiscalDto> findByNumeroDocumentoFiscal(String numeroDocumentoFiscal);
    public void removerNotaFiscal(Long idNotaFiscal);
    public NotaFiscalDto findById(Long idNotaFiscal);

}
