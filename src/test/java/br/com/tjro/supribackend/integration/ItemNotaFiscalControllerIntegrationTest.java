package br.com.tjro.supribackend.integration;

import br.com.tjro.supribackend.enums.Status;
import br.com.tjro.supribackend.model.Fornecedor;
import br.com.tjro.supribackend.model.ItemNotaFiscal;
import br.com.tjro.supribackend.model.NotaFiscal;
import br.com.tjro.supribackend.repository.FornecedorRepository;
import br.com.tjro.supribackend.repository.ItemNotaFiscalRepository;
import br.com.tjro.supribackend.repository.NotaFiscalRepository;
import br.com.tjro.supribackend.util.JwtUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest()
@AutoConfigureMockMvc(addFilters = false)
public class ItemNotaFiscalControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    NotaFiscalRepository notaFiscalRepository;

    @Autowired
    ItemNotaFiscalRepository itemNotaFiscalRepository;

    @Autowired
    FornecedorRepository fornecedorRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Mock
    Pageable pageable;

    @BeforeEach
    void up() {

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setUf("UF");
        fornecedor.setLogradouro("log");
        fornecedor.setReferenciaComercial1("ref-1");
        fornecedor.setReferenciaComercial2("ref-2");
        fornecedor.setReferenciaComercial3("ref-3");
        fornecedor.setTelefone1("tel1");
        fornecedor.setTelefone2("tel2");
        fornecedor.setBanco("banco");
        fornecedor.setBairro("bairro");
        fornecedor.setBairro("bairro");
        fornecedor.setEmailResponsavel("email@email.com");
        fornecedor.setStatus(Status.ATIVO);
        fornecedor.setNumero("numero");
        fornecedor.setCpfCnpj("cpfCnpj");
        fornecedor.setContaCorrenteDigito("contaCorrenteDigito");
        fornecedor.setAgenciaDigito("agenciaDigito");
        fornecedor.setCep("cep");
        Fornecedor fornecedorSaved = this.fornecedorRepository.save(fornecedor);

        NotaFiscal notaFiscal = new NotaFiscal();
        notaFiscal.setStatus(Status.ATIVO);
        notaFiscal.setFornecedor(fornecedorSaved);
        notaFiscal.setNumeroDocumentoFiscal("documento-fiscal");
        notaFiscal.setValorTotal(new BigDecimal(10));
        notaFiscal.setDataDocumento(LocalDate.now());
        notaFiscal.setChaveAcessoNfe("chave-acesso");
        notaFiscal.setDataAplicacao(LocalDate.now());
        notaFiscal.setNomeSuprido("nome-suprido");
        notaFiscal.setElementoDespesa("elemento-despesa");
        notaFiscal.setMatriculaSuprido("matricula-suprido");
        notaFiscal.setNumeroProcessoSei("processo-sei");
        NotaFiscal notaFiscalSaved = this.notaFiscalRepository.save(notaFiscal);


        ItemNotaFiscal itemNotaFiscal = new ItemNotaFiscal();
        itemNotaFiscal.setNotaFiscal(notaFiscalSaved);
    }

    @AfterEach
    void down() {
        this.itemNotaFiscalRepository.deleteAll();
        this.notaFiscalRepository.deleteAll();
        this.fornecedorRepository.deleteAll();
    }

    @Test
    @DisplayName("Listar item nota fiscal")
    public void listarItensNotasFiscais() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/item-nota-fiscal/lista/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
