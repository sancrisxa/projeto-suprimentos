package br.com.tjro.supribackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class SupridoDto {

    private Long idSuprido;
    @NotNull
    private String matricula;
    @NotNull
    private String nome;
}
