package br.com.tjro.supribackend.service;

import br.com.tjro.supribackend.dto.FornecedorDto;
import br.com.tjro.supribackend.model.Fornecedor;
import br.com.tjro.supribackend.projection.FornecedorProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FornecedorService {
    public FornecedorDto consultarFornecedor(Long idFornecedor);
    public FornecedorDto salvarEditarFornecedor(FornecedorDto fornecedorDto, Long idFornecedor);
    public Page<FornecedorProjection> findFornecedores(Pageable pageable, String razaoSocial, String cpfCnpj);
    public List<FornecedorProjection> findByCpfCnpj(String cpfCnpj);
}
