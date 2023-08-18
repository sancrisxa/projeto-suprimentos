package br.com.tjro.supribackend.service;

import br.com.tjro.supribackend.Exceptions.FornecedorNotFoundException;
import br.com.tjro.supribackend.Exceptions.NotaFiscalNotFoundException;
import br.com.tjro.supribackend.dto.NotaFiscalDto;
import br.com.tjro.supribackend.model.Fornecedor;
import br.com.tjro.supribackend.model.NotaFiscal;
import br.com.tjro.supribackend.projection.NotaProjectionAtivo;
import br.com.tjro.supribackend.repository.FornecedorRepository;
import br.com.tjro.supribackend.repository.NotaFiscalRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotaFiscalServiceImpl implements NotaFiscalService{

    @Autowired
    FornecedorRepository fornecedorRepository;
    @Autowired
    NotaFiscalRepository notaFiscalRepository;
    @Autowired
    ModelMapper modelMapper;

    @Transactional
    public NotaFiscalDto salvarEditarNotaFiscal(NotaFiscalDto notaFiscalDto, Long idNotaFiscal) {

        NotaFiscal notaFiscal = this.modelMapper.map(notaFiscalDto, NotaFiscal.class);

        if (idNotaFiscal != null) {
            notaFiscal.setIdNotaFiscal(idNotaFiscal);
        }

        Optional<Fornecedor> fornecedor = fornecedorRepository.findById(notaFiscalDto.getIdFornecedor());

        if (fornecedor.isEmpty()) {
            throw new FornecedorNotFoundException("Fornecedor não encontrado.");
        }

        if (fornecedor.isPresent()) {
            notaFiscal.setFornecedor(fornecedor.get());
            notaFiscal = notaFiscalRepository.save(notaFiscal);
        }
        return modelMapper.map(notaFiscal, NotaFiscalDto.class);
    }

    public Page<NotaProjectionAtivo> listarNotaFiscal(Pageable pageable) {
        Page<NotaProjectionAtivo> notasFiscais = this.notaFiscalRepository.findAllNotasAtivas(pageable);

        return notasFiscais;
    }

    private Page<NotaFiscalDto> convertNotaToNotaDto(Page<NotaFiscal> notasFiscais) {
        return notasFiscais.map(notaFiscal -> modelMapper.map(notaFiscal, NotaFiscalDto.class));
    }

    public NotaFiscalDto consultarNotaFiscal(Long idNotaFiscal) {
        Optional<NotaFiscal> notaFiscal = notaFiscalRepository.findById(idNotaFiscal);
        if (notaFiscal.isEmpty()) {
            throw new NotaFiscalNotFoundException("Nota fiscal não encontrada.");
        }
        NotaFiscalDto notaFiscalDto = this.modelMapper.map(notaFiscal, NotaFiscalDto.class);
        notaFiscalDto.setIdFornecedor(notaFiscal.get().getFornecedor().getIdFornecedor());

        return notaFiscalDto;
    }

    public List<NotaFiscalDto> findByNumeroDocumentoFiscal(String numeroDocumentoFiscal) {
        List<NotaFiscal> notasFiscais =  notaFiscalRepository.findByNumeroDocumentoFiscal(numeroDocumentoFiscal);
        List<NotaFiscalDto> notasDto =this.convertNotaToNotaDto(new PageImpl<>(notasFiscais)).stream().toList();

        return notasDto;
    }

    @Transactional
    public void removerNotaFiscal(Long idNotaFiscal) {
        this.notaFiscalRepository.deleteById(idNotaFiscal);
    }

    public NotaFiscalDto findById(Long idNotaFiscal) {
        Optional<NotaFiscal> notaFiscal = this.notaFiscalRepository.findById(idNotaFiscal);

        if (notaFiscal.isEmpty()) {
            throw new NotaFiscalNotFoundException("Nota fiscal não encontrada.");
        }

        NotaFiscalDto notaFiscalDto = this.modelMapper.map(notaFiscal, NotaFiscalDto.class);
        return notaFiscalDto;
    }
}
