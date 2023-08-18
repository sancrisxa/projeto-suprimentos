package br.com.tjro.supribackend.dto;

import br.com.tjro.supribackend.enums.StatusSolicitacaoSuprimento;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class SolicitacaoSuprimentoDto {

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
    private StatusSolicitacaoSuprimento statusSolicitacaoSuprimento;

    private String descricaoStatusSolicitacaoSuprimento;

    private String nomeSuprido;

    @JsonProperty(value = "elementosDeDespesas")
    private List<ElementoDespesaDto> elementoDespesaList;
}
