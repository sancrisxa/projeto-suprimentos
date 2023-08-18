package br.com.tjro.supribackend.dto;

import br.com.tjro.supribackend.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.stereotype.Component;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class FornecedorDto {

    private Long idFornecedor;
    @NotBlank
    @Size(min = 11, max = 14)
    private String cpfCnpj;
    private String razaoSocial;
    private String nomeFantasia;
    private String RG;
    private String inscricaoPIS;
    private String inscricaoEstadual;
    private String inscricaoMunicipal;
    @NotBlank
    private String logradouro;
    @NotBlank
    private String numero;
    @NotBlank
    private String bairro;
    private String complemento;
    @NotBlank
    private String cep;
    @NotBlank
    private String cidade;
    @NotBlank
    private String uf;
    @NotBlank
    private String telefone1;
    @NotBlank
    private String Telefone2;
    @NotBlank
    private String contato;
    @NotBlank
    private String emailResponsavel;
    private String ramoAtividade;
    private String codigoRamoAtividade;
    @NotBlank
    private String referenciaComercial1;
    @NotBlank
    private String referenciaComercial2;
    @NotBlank
    private String referenciaComercial3;
    @NotBlank
    private String banco;
    @NotBlank
    private String agenciaDigito;
    @NotBlank
    private String contaCorrenteDigito;
    @NotNull
    private Status status;
}
