package br.com.tjro.supribackend.service;

import br.com.tjro.supribackend.projection.NotaApiPublicProjection;
import br.com.tjro.supribackend.repository.NotaFiscalRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiPublicServiceImpl implements ApiPublicService {
    @Autowired
    NotaFiscalRepository notaFiscalRepository;
    @Override
    public Page<NotaApiPublicProjection> buscarNotaPorAnoMes(Pageable pageable, String ano, String mes) {

        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC,"dataDocumento"));

        Page<NotaApiPublicProjection> notas = this.notaFiscalRepository.buscarNotaPorAnoMes(
                pageRequest,
                StringUtils.isNotBlank(ano) ? ano : null,
                StringUtils.isNotBlank(mes) ? mes : null
        );

        return notas;
    }
}
