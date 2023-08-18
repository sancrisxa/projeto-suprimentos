package br.com.tjro.supribackend.service;

import br.com.tjro.supribackend.dto.ParametroSistemaDto;
import br.com.tjro.supribackend.model.ParametroSistema;
import br.com.tjro.supribackend.repository.ParametroSistemaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParametroSistemaServiceImpl implements ParametroSistemaService {

    private final ParametroSistemaRepository parametroSistemaRepository;

    @Override
    @Transactional
    public ParametroSistemaDto salvarEditarParametroSistema(ParametroSistemaDto parametroSistemaDto, Long idParametroSistema) {

        ParametroSistema parametroSistema = new ParametroSistema();
        BeanUtils.copyProperties(parametroSistemaDto, parametroSistema);

        if (idParametroSistema != null) {
            parametroSistema.setIdParametroSistema(idParametroSistema);
        }

        ParametroSistema save = parametroSistemaRepository.save(parametroSistema);
        ParametroSistemaDto parametroSistemaDtoSaved = new ParametroSistemaDto();
        BeanUtils.copyProperties(save, parametroSistemaDtoSaved);

        return parametroSistemaDtoSaved;
    }

    @Override
    public List<ParametroSistemaDto> listarParametroSistema() {
        List<ParametroSistema> parametroSistemas = this.parametroSistemaRepository.findAll();
        List<ParametroSistemaDto> parametroSistemaDtos = new ArrayList<>();
        parametroSistemas.stream().forEach(params -> {
            ParametroSistemaDto parametroSistemaDto = new ParametroSistemaDto();
            BeanUtils.copyProperties(params, parametroSistemaDto);

            parametroSistemaDtos.add(parametroSistemaDto);
        });
        return parametroSistemaDtos;
    }
}
