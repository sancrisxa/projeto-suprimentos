package br.com.tjro.supribackend.model;

import br.com.tjro.supribackend.enums.StatusSolicitacaoSuprimento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "tb_solicitacao_suprimento")
public class SolicitacaoSuprimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSolicitacaoSuprimento;
    @NotNull
    private LocalDate dataSolicitacao;
    @NotNull
    @NotEmpty
    private String matriculaSuprido;
    @NotNull
    private String matriculaUsuario;
    @NotNull
    @NotEmpty
    private String atividades;
    @NotNull
    private BigDecimal valorTotal;
    @Enumerated(EnumType.STRING)
    @NotNull
    private StatusSolicitacaoSuprimento statusSolicitacaoSuprimento;
    @Column(name = "data_liberacao")
    private LocalDate dataLiberacao;
}
