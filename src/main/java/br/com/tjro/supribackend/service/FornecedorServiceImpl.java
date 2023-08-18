package br.com.tjro.supribackend.service;

import br.com.tjro.supribackend.Exceptions.CpfCnpjDuplicateException;
import br.com.tjro.supribackend.Exceptions.CpfCnpjInvalidoException;
import br.com.tjro.supribackend.Exceptions.FornecedorNotFoundException;
import br.com.tjro.supribackend.dto.FornecedorDto;
import br.com.tjro.supribackend.model.Fornecedor;
import br.com.tjro.supribackend.projection.FornecedorProjection;
import br.com.tjro.supribackend.repository.FornecedorRepository;
import br.com.tjro.supribackend.util.ValidaCpfCnpj;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FornecedorServiceImpl implements FornecedorService{

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private ModelMapper mapper;

    public FornecedorDto consultarFornecedor(Long idFornecedor) {

        Optional<Fornecedor> fornecedorOptional = this.fornecedorRepository.findById(idFornecedor);

        if (fornecedorOptional.isEmpty()) {
            throw new FornecedorNotFoundException("Fornecedor não encontrado.");
        }

        FornecedorDto fornecedorDto = mapper.map(fornecedorOptional, FornecedorDto.class);

        return fornecedorDto;
    }

    @Transactional
    public FornecedorDto salvarEditarFornecedor(FornecedorDto fornecedorDto, Long idFornecedor) {

        if (fornecedorDto.getCpfCnpj().length() < 11) {
            throw new CpfCnpjInvalidoException("Cpf/Cnpj Inválido.");
        }

        if (fornecedorDto.getCpfCnpj().length() == 11 && !ValidaCpfCnpj.isCPF(fornecedorDto.getCpfCnpj())) {
            throw new CpfCnpjInvalidoException("Cpf/Cnpj Inválido.");
        }

        if (fornecedorDto.getCpfCnpj().length() == 14 && !ValidaCpfCnpj.isCNPJ(fornecedorDto.getCpfCnpj())) {
            throw new CpfCnpjInvalidoException("Cpf/Cnpj Inválido.");
        }


        Fornecedor fornecedor = mapper.map(fornecedorDto, Fornecedor.class);

        if (this.fornecedorRepository.findByCpfCnpj(fornecedor.getCpfCnpj()).size() > 0 && idFornecedor == null) {
            throw new CpfCnpjDuplicateException("CPF/CNPJ Já cadastrado, verifique e tente novamente.");
        }

        if (idFornecedor != null) {
            fornecedor.setIdFornecedor(idFornecedor);
        }

        Fornecedor fornecedorSaved = fornecedorRepository.save(fornecedor);

        return mapper.map(fornecedorSaved, FornecedorDto.class);
    }

    public Page<FornecedorProjection> findFornecedores(Pageable pageable, String razaoSocial, String cpfCnpj) {

        return fornecedorRepository.findFornecedores(pageable,
                StringUtils.isNotBlank(razaoSocial) ? razaoSocial.toLowerCase() : null,
                StringUtils.isNotBlank(cpfCnpj) ? cpfCnpj.toLowerCase() : null
          );
    }

    public List<FornecedorProjection> findByCpfCnpj(String cpfCnpj) {
        List<FornecedorProjection> fornecedoresByCpfCnpj = fornecedorRepository.findAllByCpfCnpj(cpfCnpj);

        if (fornecedoresByCpfCnpj.size() == 0) {
            throw new FornecedorNotFoundException("Fornecedor não encontrado.");
        }
        return fornecedoresByCpfCnpj;
    }
}
