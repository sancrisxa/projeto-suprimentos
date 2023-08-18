package br.com.tjro.supribackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDto {
    private Integer errorCode;
    private String message;
    private Object data;
}
