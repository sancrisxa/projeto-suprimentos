package br.com.tjro.supribackend.dto;

import br.com.tjro.supribackend.enums.StatusSistema;
import br.com.tjro.supribackend.enums.StatusSolicitacaoSuprimento;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ParametroSistemaDto {

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
    @Enumerated(EnumType.STRING)
    private StatusSistema statusSistema;
    @NotNull
    private BigDecimal tetoSuprimentoDimapDeaSa;
}
