package br.com.tjro.supribackend.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class XmlDto {
    private FornecedorDto fornecedorDto;
    private NotaFiscalDto notaFiscalDto;
    private List<ItemNotaFiscalDto> itemsNotasFiscaisDtos;
}
