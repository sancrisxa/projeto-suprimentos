package br.com.tjro.supribackend.service;

import br.com.tjro.supribackend.projection.NotaApiPublicProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ApiPublicService {
    public Page<NotaApiPublicProjection> buscarNotaPorAnoMes(Pageable pageable, String ano, String mes);
}
