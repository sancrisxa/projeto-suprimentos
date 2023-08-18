package br.com.tjro.supribackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_item_nota_fiscal")
public class ItemNotaFiscal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @ManyToOne(fetch = FetchType.LAZY)
    private NotaFiscal notaFiscal;
}
