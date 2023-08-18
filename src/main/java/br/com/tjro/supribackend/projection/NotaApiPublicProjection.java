package br.com.tjro.supribackend.projection;

import br.com.tjro.supribackend.enums.TipoDocumentoFiscal;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface NotaApiPublicProjection {

    Long getIdNotaFiscal();
    String getMatriculaSuprido();
    String getNumeroProcessoSei();
    String getNomeSuprido();
    String getChaveAcessoNfe();
    String getElementoDespesa();
    TipoDocumentoFiscal getTipoDocumentoFiscal();
    String getNumeroDocumentoFiscal();
    BigDecimal getValorTotal();
    LocalDate getDataAplicacao();
    LocalDate getDataDocumento();
    String getCpfCnpj();
    String getRazaoSocial();
}
