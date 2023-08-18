package br.com.tjro.supribackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ItemNotaFiscalDto {

    private Long idItemNotaFiscal;
    @NotNull
    private String descricao;
    @NotNull
    private BigDecimal quantidade;
    @NotNull
    private BigDecimal valorUnitario;
    @NotNull
    private BigDecimal desconto;
    @NotNull
    private BigDecimal valorTotal;
    @NotNull
    private Long idNotaFiscal;
}
