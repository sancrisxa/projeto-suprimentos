package br.com.tjro.supribackend.service;

import br.com.tjro.supribackend.Exceptions.DataPrazoFinalException;
import br.com.tjro.supribackend.Exceptions.DatabaseErrorException;
import br.com.tjro.supribackend.Exceptions.ErrorOcorrenciaException;
import br.com.tjro.supribackend.Exceptions.OcorrenciaNotFoundException;
import br.com.tjro.supribackend.dto.FornecedorDto;
import br.com.tjro.supribackend.dto.OcorrenciaDto;
import br.com.tjro.supribackend.model.Fornecedor;
import br.com.tjro.supribackend.model.Ocorrencia;
import br.com.tjro.supribackend.repository.OcorrenciaRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Service
public class OcorrenciaServiceImpl implements OcorrenciaService {

    @Autowired
    private OcorrenciaRepository ocorrenciaRepository;
    @Autowired
    FornecedorService fornecedorService;
    @Autowired
    ModelMapper modelMapper;

    /**
     *
     * @param ocorrenciaDto
     * @return
     */
    @Override
    public OcorrenciaDto salvarOcorrencia(OcorrenciaDto ocorrenciaDto) {

        this.validaPrazo(ocorrenciaDto);

        Ocorrencia ocorrencia = this.modelMapper.map(ocorrenciaDto, Ocorrencia.class);

        if (Objects.nonNull(ocorrenciaDto.getIdFornecedor())) {
            FornecedorDto fornecedorDto = this.fornecedorService.consultarFornecedor(ocorrenciaDto.getIdFornecedor());
            Fornecedor fornecedor = modelMapper.map(fornecedorDto, Fornecedor.class);
            ocorrencia.setFornecedor(fornecedor);
        }

        Ocorrencia ocorrenciaSaved;

        try {
            ocorrenciaSaved = this.ocorrenciaRepository.save(ocorrencia);
        } catch (Exception exception) {
            log.error("Erro ao salvar a ocorrencia {}: ", exception.getMessage());
            throw new DatabaseErrorException("Erro ao salvar ocorrencia.");
        }

        OcorrenciaDto ocorrenciaDtoResponse = modelMapper.map(ocorrenciaSaved, OcorrenciaDto.class);

        return ocorrenciaDtoResponse;
    }

    /**
     *
     * @param pageable
     * @param idFornecedor
     * @return
     */
    @Override
    public Page<OcorrenciaDto> listarOcorrencias(Pageable pageable, Long idFornecedor) {

        FornecedorDto fornecedorDto = new FornecedorDto();

        if (Objects.nonNull(idFornecedor)) {
            fornecedorDto = this.fornecedorService.consultarFornecedor(idFornecedor);
        }

        Fornecedor fornecedor = this.modelMapper.map(fornecedorDto, Fornecedor.class);

        Page<Ocorrencia> ocorrenciaRepositoryByFuncionario = null;

        try {
            ocorrenciaRepositoryByFuncionario = this.ocorrenciaRepository.findByFornecedor(pageable, fornecedor);
        } catch (Exception exception) {
            log.error("Erro ao listar as ocorrencias do Fornecedor {}  error {} :", idFornecedor, exception.getMessage());
            throw new DatabaseErrorException("Erro ao listar as ocorrencias.");
        }

        Page<OcorrenciaDto> ocorrenciaDtos = ocorrenciaRepositoryByFuncionario.map(ocorrencia -> this.modelMapper.map(ocorrencia, OcorrenciaDto.class));

        return ocorrenciaDtos;
    }

    /**
     *
     * @param ocorrenciaDto
     * @param idOcorrencia
     * @return
     */
    @Override
    public OcorrenciaDto editarOcorrencia(OcorrenciaDto ocorrenciaDto, Long idOcorrencia) {

        this.validaPrazo(ocorrenciaDto);

        Optional<Ocorrencia> optionalOcorrencia = this.ocorrenciaRepository.findById(idOcorrencia);

        if (optionalOcorrencia.isEmpty()) {
            log.error("Ocorrência não encontrado ID {} :", idOcorrencia);
            throw new OcorrenciaNotFoundException("Ocorrência não encontrada.");
        }

        Ocorrencia ocorrencia = this.modelMapper.map(optionalOcorrencia, Ocorrencia.class);

        if (Objects.nonNull(idOcorrencia)) {
            ocorrencia.setIdOcorrencia(idOcorrencia);
        } else {
            log.error("Erro ao editar ocorrencia: null");
            throw new ErrorOcorrenciaException("Erro ao editar a ocorrencia");
        }

        if (!ocorrenciaDto.getDescricao().isEmpty()) {
            ocorrencia.setDescricao(ocorrenciaDto.getDescricao());
        }

        if (Objects.nonNull(ocorrenciaDto.getDataOcorrencia())) {
            ocorrencia.setDataOcorrencia(ocorrenciaDto.getDataOcorrencia());
        }

        if (Objects.nonNull(ocorrenciaDto.getPrazo())) {
            ocorrencia.setPrazo(ocorrenciaDto.getPrazo());
        }

        if (Objects.nonNull(ocorrenciaDto.getDataFinal())) {
            ocorrencia.setDataFinal(ocorrenciaDto.getDataFinal());
        }

        if (Objects.nonNull(ocorrenciaDto.getStatusOcorrencia())) {
            ocorrencia.setStatusOcorrencia(ocorrenciaDto.getStatusOcorrencia());
        }

        if (Objects.nonNull(ocorrenciaDto.getIdFornecedor())) {
            FornecedorDto fornecedorDto = this.fornecedorService.consultarFornecedor(ocorrenciaDto.getIdFornecedor());
            Fornecedor fornecedor = modelMapper.map(fornecedorDto, Fornecedor.class);
            ocorrencia.setFornecedor(fornecedor);
        }

        Ocorrencia ocorrenciaSaved = new Ocorrencia();

        try {
            ocorrenciaSaved = this.ocorrenciaRepository.save(ocorrencia);
        } catch (Exception exception) {
            log.error("Erro ao editar a ocorrencia {} : ", exception.getMessage());
            throw new DatabaseErrorException("Erro ao editar a ocorrencia.");
        }

        OcorrenciaDto ocorrenciaDtoResponse = this.modelMapper.map(ocorrenciaSaved, OcorrenciaDto.class);

        return ocorrenciaDtoResponse;
    }

    /**
     *
     * @param idOcorrencia
     */
    @Override
    public void excluirOcorrencia(Long idOcorrencia) {

        Optional<Ocorrencia> optionalOcorrencia = this.ocorrenciaRepository.findById(idOcorrencia);

        if (optionalOcorrencia.isEmpty()) {
            log.error("Ocorrência não encontrada ID {} : ", idOcorrencia);
            throw new OcorrenciaNotFoundException("Ocorrencia não encontrada");
        }

        Ocorrencia ocorrencia = this.modelMapper.map(optionalOcorrencia, Ocorrencia.class);
        ocorrencia.setIdOcorrencia(idOcorrencia);

        try {
            this.ocorrenciaRepository.delete(ocorrencia);
        } catch (Exception exception) {
            log.error("Erro ao deletar a ocorrencia de ID {} eroor {} : ", idOcorrencia, exception.getMessage());
            throw new DatabaseErrorException("Erro ao deletar a ocorrência");
        }
    }

    /**
     *
     * @param idOcorrencia
     * @return
     */
    @Override
    public OcorrenciaDto findByIdOcorrencia(Long idOcorrencia) {

        Optional<Ocorrencia> optionalOcorrencia = this.ocorrenciaRepository.findById(idOcorrencia);

        if (optionalOcorrencia.isEmpty()) {
            log.error("Ocorrência não encontrada ID {} : ", idOcorrencia);
            throw new OcorrenciaNotFoundException("Ocorrência não encontrada.");
        }

        OcorrenciaDto ocorrenciaDto = this.modelMapper.map(optionalOcorrencia, OcorrenciaDto.class);
        return ocorrenciaDto;
    }

    /**
     *
     * @param ocorrenciaDto
     */
    private void validaPrazo(OcorrenciaDto ocorrenciaDto) {
        if (Objects.nonNull(ocorrenciaDto.getPrazo()) && Objects.nonNull(ocorrenciaDto.getDataFinal()) && Objects.nonNull(ocorrenciaDto.getDataFinal())) {
            LocalDate dataAcrescidaPrazo = ocorrenciaDto.getDataOcorrencia().plusDays(ocorrenciaDto.getPrazo());
            if (dataAcrescidaPrazo.compareTo(ocorrenciaDto.getDataFinal()) != 0) {
                log.error("Data final {} não confere com o prazo {} : ", ocorrenciaDto.getDataFinal(), ocorrenciaDto.getPrazo());
                throw new DataPrazoFinalException("Data final não confere com o prazo informado.");
            }
        }
    }
}
