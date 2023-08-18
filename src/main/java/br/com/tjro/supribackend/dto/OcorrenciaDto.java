package br.com.tjro.supribackend.dto;

import br.com.tjro.supribackend.enums.StatusOcorrencia;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Data
@Component
public class OcorrenciaDto {

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
    private Long idFornecedor;
}
