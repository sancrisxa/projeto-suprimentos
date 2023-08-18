package br.com.tjro.supribackend.controller;

import br.com.tjro.supribackend.dto.NotaComItemDto;
import br.com.tjro.supribackend.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/nota-fiscal")
public class ApiController {

    @Autowired
    private final ApiService apiService;
    @GetMapping("/lista")
    public ResponseEntity<List<NotaComItemDto>> buscarNotaPorData(Pageable pageable,
                                                                  @RequestParam(value = "dataInicial", required = false) LocalDate dataInicial,
                                                                  @RequestParam(value = "dataFinal", required = false) LocalDate dataFinal
    ) {
        List<NotaComItemDto> notas = apiService.buscarNotaPorData(pageable,
                dataInicial,
                dataFinal);

        return ResponseEntity.ok(notas);
    }
}

