package br.com.tjro.supribackend.model;

import br.com.tjro.supribackend.enums.StatusOcorrencia;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_ocorrencias")
public class Ocorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOcorrencia;
    @NotNull
    private String descricao;
    private Integer prazo;
    @NotNull
    private LocalDate dataOcorrencia;
    private LocalDate dataFinal;
    @NotNull
    private StatusOcorrencia statusOcorrencia;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Fornecedor fornecedor;
}
