package br.com.tjro.supribackend.service;

import br.com.tjro.supribackend.Exceptions.DatabaseErrorException;
import br.com.tjro.supribackend.Exceptions.SupridoInexistenteException;
import br.com.tjro.supribackend.dto.SupridoDto;
import br.com.tjro.supribackend.model.Suprido;
import br.com.tjro.supribackend.repository.SupridoRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class SupridoServiceImpl implements SupridoService {

    @Autowired
    private SupridoRepository supridoRepository;

    @Autowired
    @Lazy
    SolicitacaoSuprimentoService solicitacaoSuprimentoService;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Page<SupridoDto> listarSuprido(Pageable pageable) {

        Page<Suprido> supridos;

        try {
            supridos = this.supridoRepository.findAllBaseEgesp(pageable);
        } catch (Exception exception) {
            log.error("Erro ao buscar suprido na egesp {} :", exception.getMessage());
            throw new DatabaseErrorException("Erro ao buscar na base da egesp.");
        }

        Page<SupridoDto> supridoDtos = this.converter(supridos);

        return supridoDtos;
    }

    @Override
    public SupridoDto buscarSupridoMatricula(String matricula) {

        Optional<Suprido> suprido = this.supridoRepository.findByMatricula(matricula);

        if (suprido.isEmpty()) {
            throw new SupridoInexistenteException("Suprido não encontrado");
        }

        SupridoDto supridoDto = this.mapper.map(suprido, SupridoDto.class);

        return supridoDto;
    }

    private Page<SupridoDto> converter(Page<Suprido> supridos) {
        return supridos.map(
                suprido -> this.mapper.map(suprido, SupridoDto.class)
        );
    }
}
