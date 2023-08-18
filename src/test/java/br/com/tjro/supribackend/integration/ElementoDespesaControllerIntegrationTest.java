package br.com.tjro.supribackend.integration;

import br.com.tjro.supribackend.enums.StatusSolicitacaoSuprimento;
import br.com.tjro.supribackend.model.ElementoDespesa;
import br.com.tjro.supribackend.model.SolicitacaoSuprimento;
import br.com.tjro.supribackend.model.Suprido;
import br.com.tjro.supribackend.repository.ElementoDespesaRepository;
import br.com.tjro.supribackend.repository.SolicitacaoSuprimentoRepository;
import br.com.tjro.supribackend.repository.SupridoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest()
@AutoConfigureMockMvc(addFilters = false)
public class ElementoDespesaControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    SolicitacaoSuprimentoRepository solicitacaoSuprimentoRepository;

    @Autowired
    ElementoDespesaRepository elementoDespesaRepository;

    @Autowired
    SupridoRepository supridoRepository;

    @BeforeEach
    void up() {

        SolicitacaoSuprimento solicitacaoSuprimento = new SolicitacaoSuprimento();
        solicitacaoSuprimento.setMatriculaSuprido("123");
        solicitacaoSuprimento.setMatriculaSuprido("matricula");
        solicitacaoSuprimento.setDataSolicitacao(LocalDate.now());
        solicitacaoSuprimento.setStatusSolicitacaoSuprimento(StatusSolicitacaoSuprimento.SOLICITADO);
        solicitacaoSuprimento.setValorTotal(new BigDecimal(10.00));
        solicitacaoSuprimento.setAtividades("123");
        solicitacaoSuprimento.setMatriculaUsuario("123456");
        SolicitacaoSuprimento solicitacaoSuprimentoSaved = this.solicitacaoSuprimentoRepository.save(solicitacaoSuprimento);

        ElementoDespesa elementoDespesa = new ElementoDespesa();

        elementoDespesa.setSolicitacaoSuprimento(solicitacaoSuprimentoSaved);
        elementoDespesa.setDescricao("Descricao");
        elementoDespesa.setValor(new BigDecimal(10.00));

    }

    @AfterEach
    void down() {

        this.elementoDespesaRepository.deleteAll();
        this.solicitacaoSuprimentoRepository.deleteAll();
    }

    @Test
    @DisplayName("Listar elementos de despesas")
    public void listarElementoDespesa() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/elemento-despesa/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
