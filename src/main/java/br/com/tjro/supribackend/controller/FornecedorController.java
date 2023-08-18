package br.com.tjro.supribackend.controller;

import br.com.tjro.supribackend.dto.FornecedorDto;
import br.com.tjro.supribackend.dto.ResponseInsertDto;
import br.com.tjro.supribackend.projection.FornecedorProjection;
import br.com.tjro.supribackend.service.FornecedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fornecedor")
public class FornecedorController {

    private final FornecedorService fornecedorService;

    @PostMapping
    public ResponseEntity<ResponseInsertDto> salvarFornecedor(@Valid @RequestBody FornecedorDto fornecedor) throws Exception {

        FornecedorDto save = fornecedorService.salvarEditarFornecedor(fornecedor, null);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseInsertDto(save != null ? save.getIdFornecedor() : null));
    }

    @PutMapping("/{id}")
    public ResponseEntity editarFornecedor(@Valid @RequestBody FornecedorDto fornecedor, @PathVariable(value = "id") Long id) throws Exception {
        fornecedorService.salvarEditarFornecedor(fornecedor, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FornecedorDto> consultarFornecedor(@PathVariable(value = "id") Long id) {
        FornecedorDto fornecedorDto = fornecedorService.consultarFornecedor(id);
        return ResponseEntity.ok(fornecedorDto);
    }

    @GetMapping("/cpf-cnpj/{cpfCnpj}")
    public ResponseEntity<List<FornecedorProjection>> consultarCpfCnpj(@PathVariable(value = "cpfCnpj") String cpfCnpj) {
        List<FornecedorProjection> fornecedorProjectionList = fornecedorService.findByCpfCnpj(cpfCnpj);
        return ResponseEntity.ok(fornecedorProjectionList);
    }

    @GetMapping
    public ResponseEntity<Page<FornecedorProjection>> pesquisarFornecedor(Pageable pageable,
                                                                          @RequestParam(value = "razaoSocial", required = false) String razaoSocial,
                                                                          @RequestParam(value = "cpfCnpj", required = false) String cpfCnpj
                                                                  ) {
        Page<FornecedorProjection> fornecedor = fornecedorService.findFornecedores(pageable,
                razaoSocial,
                cpfCnpj);
        return ResponseEntity.ok(fornecedor);
    }
}
