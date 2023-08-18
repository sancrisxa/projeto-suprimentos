package br.com.tjro.supribackend.projection;

import br.com.tjro.supribackend.enums.Status;

public interface FornecedorProjection {
    Long getIdFornecedor();
    String getRazaoSocial();
    String getNomeFantasia();
    String getCpfCnpj();
    String getRG();
    String getInscricaoMunicipal();
    String getLogradouro();
    String getNumero();
    String getBairro();
    String getComplemento();
    String getCep();
    String getCidade();
    String getUf();
    String getTelefone1();
    String getTelefone2();
    String getContato();
    String getEmailResponsavel();
    String getRamoAtividade();
    String getCodigoRamoAtividade();
    String getReferenciaComercial1();
    String getReferenciaComercial2();
    String getReferenciaComercial3();
    String getBanco();
    String getAgenciaDigito();
    String getContaCorrenteDigito();
    Status getStatus();
}
