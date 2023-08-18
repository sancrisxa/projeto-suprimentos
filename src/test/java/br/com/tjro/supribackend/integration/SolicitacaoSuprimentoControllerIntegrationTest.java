package br.com.tjro.supribackend.integration;

import br.com.tjro.supribackend.enums.StatusSolicitacaoSuprimento;
import br.com.tjro.supribackend.model.SolicitacaoSuprimento;
import br.com.tjro.supribackend.model.Suprido;
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
public class SolicitacaoSuprimentoControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    SolicitacaoSuprimentoRepository solicitacaoSuprimentoRepository;

    @BeforeEach
    public void up() {

        SolicitacaoSuprimento solicitacaoSuprimento = new SolicitacaoSuprimento();
        solicitacaoSuprimento.setDataSolicitacao(LocalDate.now());
        solicitacaoSuprimento.setStatusSolicitacaoSuprimento(StatusSolicitacaoSuprimento.SOLICITADO);
        solicitacaoSuprimento.setMatriculaSuprido("123");
        solicitacaoSuprimento.setMatriculaUsuario("123");
        solicitacaoSuprimento.setValorTotal(new BigDecimal(12.00));
        solicitacaoSuprimento.setMatriculaSuprido("matricula-suprido");
        solicitacaoSuprimento.setAtividades("123");
        this.solicitacaoSuprimentoRepository.save(solicitacaoSuprimento);
    }

    @AfterEach
    public void down() {
        this.solicitacaoSuprimentoRepository.deleteAll();
    }

    @Test
    @DisplayName("Listar solicitacoes de suprimentos")
    void listarSolicitacaoSuprimento() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/solicitacao-suprimento"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

}
