package br.com.tjro.supribackend.dto;

import br.com.tjro.supribackend.model.SolicitacaoSuprimento;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ElementoDespesaDto {
    private Long idElementoDespesa;
    @NotNull
    @NotEmpty
    private String descricao;
    @NotNull
    private BigDecimal valor;
    @NotNull
    private SolicitacaoSuprimento solicitacaoSuprimento;
}
