package br.com.tjro.supribackend.model;

import br.com.tjro.supribackend.enums.Status;
import br.com.tjro.supribackend.enums.TipoDocumentoFiscal;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_nota_fiscal")
public class NotaFiscal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNotaFiscal;
    private String matriculaSuprido;
    @NotNull
    private String numeroProcessoSei;
    @NotNull
    private String nomeSuprido;
    @Column(name = "chave_acesso_nf_e")
    @NotNull
    private String chaveAcessoNfe;
    @NotNull
    private String elementoDespesa;
    @Enumerated(EnumType.STRING)
    private TipoDocumentoFiscal tipoDocumentoFiscal;
    @NotNull
    private String numeroDocumentoFiscal;
    @NotNull
    private BigDecimal valorTotal;
    @NotNull
    private LocalDate dataAplicacao;
    @NotNull
    private LocalDate dataDocumento;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Fornecedor fornecedor;
}
