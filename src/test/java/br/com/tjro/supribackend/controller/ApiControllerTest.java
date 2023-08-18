package br.com.tjro.supribackend.controller;

import br.com.tjro.supribackend.dto.NotaComItemDto;
import br.com.tjro.supribackend.service.ApiService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
public class ApiControllerTest {
    
    @Mock
    ApiService apiService;

    @Mock
    Pageable pageable;
    
    @InjectMocks
    ApiController apiController;
    
    @Test
    @DisplayName("listar todas as notas fiscais por data")
    public void buscarNotaPorData() {

        ResponseEntity<List<NotaComItemDto>> responseEntity = this.apiController.buscarNotaPorData(pageable, LocalDate.now(), LocalDate.now());

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
