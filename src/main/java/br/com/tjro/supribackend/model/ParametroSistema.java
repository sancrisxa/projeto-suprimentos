package br.com.tjro.supribackend.model;

import br.com.tjro.supribackend.enums.StatusSistema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_parametro_sistema")
public class ParametroSistema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idParametroSistema;
    @NotNull
    private Integer prazoPrestacaoConta;
    @NotNull
    private Integer prazoAplicacao;
    @NotNull
    private LocalDate dataLimitePrestacaoConta;
    @NotNull
    private LocalDate dataLimiteConcessao;
    @NotNull
    private LocalDate dataLimiteAplicacaoAnual;
    @NotNull
    private BigDecimal tetoNotaFiscal;
    @NotNull
    private BigDecimal tetoSuprimento;
    @NotNull
    private BigDecimal tetoSuprimentoDimapDeaSa;
    @Enumerated(EnumType.STRING)
    private StatusSistema statusSistema;
}
