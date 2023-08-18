package br.com.tjro.supribackend.integration;

import br.com.tjro.supribackend.dto.NotaFiscalDto;
import br.com.tjro.supribackend.enums.Status;
import br.com.tjro.supribackend.enums.TipoDocumentoFiscal;
import br.com.tjro.supribackend.model.Fornecedor;
import br.com.tjro.supribackend.model.NotaFiscal;
import br.com.tjro.supribackend.repository.FornecedorRepository;
import br.com.tjro.supribackend.repository.NotaFiscalRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest()
@AutoConfigureMockMvc(addFilters = false)
public class NotaFiscalControllerIntegrationTest {

    private Fornecedor fornecedor;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    NotaFiscalRepository notaFiscalRepository;

    @Autowired
    FornecedorRepository fornecedorRepository;

    @Autowired
    ObjectMapper mapper;

    @BeforeEach
    public void up() {

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
        this.fornecedor = this.fornecedorRepository.save(fornecedor);

        NotaFiscal notaFiscal = new NotaFiscal();
        notaFiscal.setStatus(Status.ATIVO);
        notaFiscal.setFornecedor(this.fornecedor);
        notaFiscal.setNumeroDocumentoFiscal("documento-fiscal");
        notaFiscal.setValorTotal(new BigDecimal(10));
        notaFiscal.setDataDocumento(LocalDate.now());
        notaFiscal.setChaveAcessoNfe("chave-acesso");
        notaFiscal.setDataAplicacao(LocalDate.now());
        notaFiscal.setNomeSuprido("nome-suprido");
        notaFiscal.setElementoDespesa("elemento-despesa");
        notaFiscal.setMatriculaSuprido("matricula-suprido");
        notaFiscal.setNumeroProcessoSei("processo-sei");
        this.notaFiscalRepository.save(notaFiscal);
    }

    @AfterEach
    public void down() {
        this.notaFiscalRepository.deleteAll();
        this.fornecedorRepository.deleteAll();
    }

    @Test
    @DisplayName("Listar notas fiscais")
    void listarNotaFiscal() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/nota-fiscal"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Salva nota fiscal com sucesso")
    void salvarNotaFiscal() throws Exception {

        NotaFiscalDto notaFiscalDto = new NotaFiscalDto();
        notaFiscalDto.setStatus(Status.ATIVO);
        notaFiscalDto.setIdFornecedor(this.fornecedor.getIdFornecedor());
        notaFiscalDto.setNumeroDocumentoFiscal("documento-fiscal");
        notaFiscalDto.setValorTotal(new BigDecimal(10));
        notaFiscalDto.setDataDocumento(LocalDate.now());
        notaFiscalDto.setChaveAcessoNfe("chave-acesso");
        notaFiscalDto.setDataAplicacao(LocalDate.now());
        notaFiscalDto.setNomeSuprido("nome-suprido");
        notaFiscalDto.setElementoDespesa("elemento-despesa");
        notaFiscalDto.setMatriculaSuprido("matricula-suprido");
        notaFiscalDto.setNumeroProcessoSei("processo-sei");
        notaFiscalDto.setTipoDocumentoFiscal(TipoDocumentoFiscal.NOTA_FISCAL);

        String notaFiscalRequest = this.mapper.writeValueAsString(notaFiscalDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/nota-fiscal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(notaFiscalRequest)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    @DisplayName("Editar nota fiscal com sucesso")
    void editarNotaFiscal() throws Exception {

        NotaFiscalDto notaFiscalDto = new NotaFiscalDto();
        notaFiscalDto.setStatus(Status.ATIVO);
        notaFiscalDto.setIdFornecedor(this.fornecedor.getIdFornecedor());
        notaFiscalDto.setNumeroDocumentoFiscal("documento-fiscal");
        notaFiscalDto.setValorTotal(new BigDecimal(10));
        notaFiscalDto.setDataDocumento(LocalDate.now());
        notaFiscalDto.setChaveAcessoNfe("chave-acesso");
        notaFiscalDto.setDataAplicacao(LocalDate.now());
        notaFiscalDto.setNomeSuprido("nome-suprido");
        notaFiscalDto.setElementoDespesa("elemento-despesa");
        notaFiscalDto.setMatriculaSuprido("matricula-suprido");
        notaFiscalDto.setNumeroProcessoSei("processo-sei");
        notaFiscalDto.setTipoDocumentoFiscal(TipoDocumentoFiscal.NOTA_FISCAL);

        String notaFiscalRequest = this.mapper.writeValueAsString(notaFiscalDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/nota-fiscal/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(notaFiscalRequest)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
