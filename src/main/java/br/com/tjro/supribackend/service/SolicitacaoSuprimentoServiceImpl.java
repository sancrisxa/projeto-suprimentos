package br.com.tjro.supribackend.service;

import br.com.tjro.supribackend.Exceptions.ErroAoSalvarException;
import br.com.tjro.supribackend.Exceptions.SupridoInexistenteException;
import br.com.tjro.supribackend.dto.SolicitacaoSuprimentoDto;
import br.com.tjro.supribackend.dto.SupridoDto;;
import br.com.tjro.supribackend.model.SolicitacaoSuprimento;
import br.com.tjro.supribackend.repository.SolicitacaoSuprimentoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class SolicitacaoSuprimentoServiceImpl implements SolicitacaoSuprimentoService {

    @Autowired
    private SolicitacaoSuprimentoRepository solicitacaoSuprimentoRepository;

    @Autowired
    private SupridoService supridoService;

    @Autowired
    ModelMapper modelMapper;

    @Transactional
    @Override
    public SolicitacaoSuprimentoDto salvarEditarSolicitacaoSuprimento(SolicitacaoSuprimentoDto solicitacaoSuprimentoDto, Long idSolicitacaoSuprimento) {

        SupridoDto supridoDto = this.supridoService.buscarSupridoMatricula(solicitacaoSuprimentoDto.getMatriculaSuprido());

        if (supridoDto == null) {
            throw new SupridoInexistenteException("Suprido Inexistente");
        }

        SolicitacaoSuprimento solicitacaoSuprimentoSaved;

        if (idSolicitacaoSuprimento != null) {
            solicitacaoSuprimentoDto.setIdSolicitacaoSuprimento(idSolicitacaoSuprimento);
        }

        try {

            solicitacaoSuprimentoSaved = this.solicitacaoSuprimentoRepository.save(this.modelMapper.map(solicitacaoSuprimentoDto, SolicitacaoSuprimento.class));

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ErroAoSalvarException("Erro ao fazer a requisição");
        }

        return this.modelMapper.map(solicitacaoSuprimentoSaved, SolicitacaoSuprimentoDto.class);
    }

    @Override
    public SolicitacaoSuprimentoDto findById(Long idSolicitacaoSuprimento) {

        Optional<SolicitacaoSuprimento> optionalSolicitacaoSuprimento = this.solicitacaoSuprimentoRepository.findById(idSolicitacaoSuprimento);

        if (optionalSolicitacaoSuprimento.isEmpty()) {
            throw new SupridoInexistenteException("Solicitação de Surprimento não encontrado");
        }

        return modelMapper.map(optionalSolicitacaoSuprimento, SolicitacaoSuprimentoDto.class);
    }

    @Override
    public Page<SolicitacaoSuprimentoDto> listarSuprimentoSolicitacao(Pageable pageable) {

        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.ASC,"dataSolicitacao"));

        Page<SolicitacaoSuprimento> solicitacaoSuprimentos = this.solicitacaoSuprimentoRepository.findAll(pageRequest);

        return this.convertToSolicitacaoSuprimentoDto(solicitacaoSuprimentos);
    }

    @Override
    @Transactional
    public void removerSolicitacao(Long idSolicitacao) {
        try {
            this.solicitacaoSuprimentoRepository.deleteById(idSolicitacao);
        }catch (Exception e) {
            log.error("Erro ao remover a solicitação: {}", e.getMessage());
            throw new ErroAoSalvarException("Erro ao fazer a requisição");
        }
    }

    @Override
    public List<SolicitacaoSuprimentoDto> findSolicitacaoSuprimentoBySuprido(String matricula) {

        List<SolicitacaoSuprimento> solicitacaoSuprimentoBySuprido = this.solicitacaoSuprimentoRepository.findSolicitacaoSuprimentoBySuprido(matricula);
        Page<SolicitacaoSuprimentoDto> solicitacaoSuprimentoDtos = this.convertToSolicitacaoSuprimentoDto(new PageImpl<>(solicitacaoSuprimentoBySuprido));
        return solicitacaoSuprimentoDtos.toList();
    }

    private Page<SolicitacaoSuprimentoDto> convertToSolicitacaoSuprimentoDto(Page<SolicitacaoSuprimento> solicitacaoSuprimentos) {

        return solicitacaoSuprimentos.map(solicitacaoSuprimento -> modelMapper.map(solicitacaoSuprimento, SolicitacaoSuprimentoDto.class));
    }
}
