package br.com.tjro.supribackend.service;

import br.com.tjro.supribackend.Exceptions.ErroAoSalvarException;
import br.com.tjro.supribackend.Exceptions.SolicitacaoComDespesaException;
import br.com.tjro.supribackend.dto.ElementoDespesaDto;
import br.com.tjro.supribackend.dto.SolicitacaoSuprimentoDto;
import br.com.tjro.supribackend.model.ElementoDespesa;
import br.com.tjro.supribackend.model.SolicitacaoSuprimento;
import br.com.tjro.supribackend.repository.ElementoDespesaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class ElementoDespesaServiceImpl implements ElementoDespesaService{

    @Autowired
    private ElementoDespesaRepository elementoDespesaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public List<ElementoDespesaDto> salvarEditarElementoDespesa(List<ElementoDespesaDto> elementosDespesaDtos, SolicitacaoSuprimentoDto solicitacaoSuprimentoDto) {

        List<ElementoDespesaDto> elementoDespesaDtoList = new ArrayList<>();
        elementosDespesaDtos.stream().forEach(elementoDespesaDto -> {
            ElementoDespesa elementoDespesa = modelMapper.map(elementoDespesaDto, ElementoDespesa.class);
            elementoDespesa.setSolicitacaoSuprimento(modelMapper.map(solicitacaoSuprimentoDto, SolicitacaoSuprimento.class));
            ElementoDespesa save = this.elementoDespesaRepository.save(elementoDespesa);
            elementoDespesaDtoList.add(modelMapper.map(save, ElementoDespesaDto.class));
        });

        return elementoDespesaDtoList;
    }

    @Override
    public Page<ElementoDespesaDto> listarElementoDespesa(Pageable pageable, Long idSolicitacao) {

        Page<ElementoDespesa> elementoDespesas = this.elementoDespesaRepository.findAllBySolicitacao_Id(pageable, idSolicitacao);
        List<ElementoDespesaDto> elementoDespesaDtos = this.converter(elementoDespesas);

        return new PageImpl<>(elementoDespesaDtos);
    }

    @Transactional
    public ElementoDespesaDto salvarEditarElementoDespesaUnico(ElementoDespesaDto elementoDespesaDto, Long idElementoDespesa) {

        if (idElementoDespesa != null) {
            elementoDespesaDto.setIdElementoDespesa(idElementoDespesa);
        }

        ElementoDespesa elementoDespesa = this.elementoDespesaRepository.save(modelMapper.map(elementoDespesaDto, ElementoDespesa.class));

        return modelMapper.map(elementoDespesa, ElementoDespesaDto.class);
    }

    @Override
    public void removerElementoDespesa(Long idElementoDespesa) {
        try {
            this.elementoDespesaRepository.deleteById(idElementoDespesa);
        } catch (Exception e) {
            log.error("Error ao remover o elemento de despesa: {}", e.getMessage());
            throw new ErroAoSalvarException("Erro ao fazer a requisição");
        }

    }

    @Override
    public List<ElementoDespesaDto> buscarElementoDespesaByIdSolicitacao(Long idSolicitacao) {
        try {

            List<ElementoDespesa> elementoDespesas = this.elementoDespesaRepository.buscarElementoDespesaByIdSolicitacao(idSolicitacao);
            List<ElementoDespesaDto> elementoDespesaDtos = this.converter(new PageImpl<>(elementoDespesas));

            return elementoDespesaDtos;
        } catch (Exception e) {
            log.error("Erro ao listar a solicitação id: {} message: {}", idSolicitacao, e.getMessage());
            throw new SolicitacaoComDespesaException("Erro ao buscar a solicitação");
        }
    }

    @Override
    public ElementoDespesaDto findById(Long idElementoDespesa) {
        Optional<ElementoDespesa> elementoDespesa = this.elementoDespesaRepository.findById(idElementoDespesa);
        return modelMapper.map(elementoDespesa, ElementoDespesaDto.class);
    }

    public List<ElementoDespesaDto> converter(Page<ElementoDespesa> elementoDespesas) {
        return elementoDespesas.stream().map(
                elementoDespesa -> modelMapper.map(elementoDespesa, ElementoDespesaDto.class)
        ).collect(Collectors.toList());
    }


}
