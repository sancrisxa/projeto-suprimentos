package br.com.tjro.supribackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorObjectDto {
    private String message;
    private String field;
    private Object parameter;
}
