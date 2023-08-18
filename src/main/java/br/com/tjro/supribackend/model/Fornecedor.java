package br.com.tjro.supribackend.model;

import br.com.tjro.supribackend.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_fornecedor ")
public class Fornecedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFornecedor;
    private String razaoSocial;
    private String nomeFantasia;
    @Column(name = "CPF_CNPJ", unique = true)
    @NotBlank
    private String cpfCnpj;
    private String RG;
    @Column(name = "inscricao_PIS")
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
    private String cidade;
    @Column(name = "UF")
    @NotBlank
    private String uf;
    @Column(name = "telefone_1")
    @NotBlank
    private String telefone1;
    @Column(name = "telefone_2")
    @NotBlank
    private String Telefone2;
    private String contato;
    @NotBlank
    private String emailResponsavel;
    private String ramoAtividade;
    private String codigoRamoAtividade;
    @Column(name = "referencia_comercial_1")
    @NotBlank
    private String referenciaComercial1;
    @Column(name = "referencia_comercial_2")
    @NotBlank
    private String referenciaComercial2;
    @Column(name = "referencia_comercial_3")
    @NotBlank
    private String referenciaComercial3;
    @NotBlank
    private String banco;
    @Column(name = "agencia_digito_agencia")
    @NotBlank
    private String agenciaDigito;
    @Column(name = "conta_corrente_digito_conta_corrente")
    @NotBlank
    private String contaCorrenteDigito;
    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status;
}
