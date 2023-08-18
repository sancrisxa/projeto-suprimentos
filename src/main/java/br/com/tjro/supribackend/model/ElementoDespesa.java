package br.com.tjro.supribackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "tb_elemento_despesa")
public class ElementoDespesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idElementoDespesa;
    @NotNull
    @NotEmpty
    private String descricao;
    @NotNull
    private BigDecimal valor;
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private SolicitacaoSuprimento solicitacaoSuprimento;
}
