package br.com.tjro.supribackend.integration;

import br.com.tjro.supribackend.dto.FornecedorDto;
import br.com.tjro.supribackend.enums.Status;
import br.com.tjro.supribackend.model.Fornecedor;
import br.com.tjro.supribackend.repository.FornecedorRepository;
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

@SpringBootTest()
@AutoConfigureMockMvc(addFilters = false)
public class FornecedorControllerIntegrationTest {

    private Fornecedor fornecedorSaved;

    @Autowired
    FornecedorRepository fornecedorRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @BeforeEach
    void up() {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setCpfCnpj("123");
        fornecedor.setLogradouro("log");
        fornecedor.setNumero("12");
        fornecedor.setBairro("bairro");
        fornecedor.setCep("cep");
        fornecedor.setTelefone1("tel1");
        fornecedor.setTelefone2("tel2");
        fornecedor.setEmailResponsavel("email");
        fornecedor.setReferenciaComercial1("ref1");
        fornecedor.setReferenciaComercial2("ref2");
        fornecedor.setReferenciaComercial3("ref3");
        fornecedor.setBanco("banco");
        fornecedor.setAgenciaDigito("123");
        fornecedor.setContaCorrenteDigito("123");
        fornecedor.setStatus(Status.ATIVO);
        fornecedor.setUf("MG");

        fornecedorSaved = this.fornecedorRepository.save(fornecedor);
    }

    @AfterEach
    void down() {
        this.fornecedorRepository.deleteAll();
    }

    @Test
    @DisplayName("Listar fornecedores")
    void pesquisarFornecedor() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/fornecedor"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Salva fornecedor com sucesso")
    void salvarFornecedor() throws Exception {
        FornecedorDto fornecedorDto = new FornecedorDto();
        fornecedorDto.setCpfCnpj("1234567891011");
        fornecedorDto.setLogradouro("log");
        fornecedorDto.setNumero("12");
        fornecedorDto.setBairro("bairro");
        fornecedorDto.setCep("cep");
        fornecedorDto.setCidade("cidade");
        fornecedorDto.setContato("contato");
        fornecedorDto.setTelefone1("tel1");
        fornecedorDto.setTelefone2("tel2");
        fornecedorDto.setEmailResponsavel("email");
        fornecedorDto.setReferenciaComercial1("ref1");
        fornecedorDto.setReferenciaComercial2("ref2");
        fornecedorDto.setReferenciaComercial3("ref3");
        fornecedorDto.setBanco("banco");
        fornecedorDto.setAgenciaDigito("123");
        fornecedorDto.setContaCorrenteDigito("123");
        fornecedorDto.setStatus(Status.ATIVO);
        fornecedorDto.setUf("MG");


        String fornecedorRequest = mapper.writeValueAsString(fornecedorDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/fornecedor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(fornecedorRequest)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Editar fornecedor com sucesso")
    void editarFornecedor() throws Exception {
        FornecedorDto fornecedorDto = new FornecedorDto();
        fornecedorDto.setCpfCnpj("1234567891011");
        fornecedorDto.setLogradouro("log");
        fornecedorDto.setNumero("12");
        fornecedorDto.setBairro("bairro");
        fornecedorDto.setCep("cep");
        fornecedorDto.setCidade("cidade");
        fornecedorDto.setContato("contato");
        fornecedorDto.setTelefone1("tel1");
        fornecedorDto.setTelefone2("tel2");
        fornecedorDto.setEmailResponsavel("email");
        fornecedorDto.setReferenciaComercial1("ref1");
        fornecedorDto.setReferenciaComercial2("ref2");
        fornecedorDto.setReferenciaComercial3("ref3");
        fornecedorDto.setBanco("banco");
        fornecedorDto.setAgenciaDigito("123");
        fornecedorDto.setContaCorrenteDigito("123");
        fornecedorDto.setStatus(Status.ATIVO);
        fornecedorDto.setUf("MG");


        String fornecedorRequest = mapper.writeValueAsString(fornecedorDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/fornecedor/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(fornecedorRequest)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Consultar fornecedores")
    void consultarFornecedor() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/fornecedor/" + fornecedorSaved.getIdFornecedor()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Consultar fornecedores por cpf/cnpj")
    void consultarCpfCnpj() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/fornecedor/cpf-cnpj/123"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
