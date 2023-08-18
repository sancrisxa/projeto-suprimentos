package br.com.tjro.supribackend.dto;

import br.com.tjro.supribackend.enums.TipoDocumentoFiscal;
import lombok.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class NotaComItemDto {
    private String matriculaSuprido;
    private String numeroProcessoSei;
    private String nomeSuprido;
    private String chaveAcessoNfe;
    private String elementoDespesa;
    private TipoDocumentoFiscal tipoDocumentoFiscal;
    private String numeroDocumentoFiscal;
    private BigDecimal valorTotal;
    private LocalDate dataAplicacao;
    private LocalDate dataDocumento;
    private String razaoSocialFornecedor;
    private List<ItemNotaFiscalDto> itemNotaFiscal;
}
