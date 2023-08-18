package br.com.tjro.supribackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "GEGESP.VW_SPS_RELACAO_SUPRIDOS@ORASRH")
public class Suprido {

    @Id
    @Column(name = "matricula")
    @NotNull
    private String matricula;

    @Column(name = "nome_servidor")
    @NotNull
    private String nome;
}
