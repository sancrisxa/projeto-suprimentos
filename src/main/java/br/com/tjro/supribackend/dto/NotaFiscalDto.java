package br.com.tjro.supribackend.dto;

import br.com.tjro.supribackend.enums.Status;
import br.com.tjro.supribackend.enums.TipoDocumentoFiscal;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class NotaFiscalDto {

    private Long idNotaFiscal;
    private String matriculaSuprido;
    @NotNull
    private String numeroProcessoSei;
    @NotNull
    private String nomeSuprido;
    @NotNull
    private String chaveAcessoNfe;
    @NotNull
    private String elementoDespesa;
    @NotNull
    private TipoDocumentoFiscal tipoDocumentoFiscal;
    @NotNull
    private String numeroDocumentoFiscal;
    @NotNull
    private BigDecimal valorTotal;
    @NotNull
    private LocalDate dataAplicacao;
    @NotNull
    private LocalDate dataDocumento;
    private String razaoSocialFornecedor;
    @NotNull
    private Status status;
    @NotNull
    private Long idFornecedor;

}
