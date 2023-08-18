package br.com.tjro.supribackend.service;

import br.com.tjro.supribackend.dto.ElementoDespesaDto;
import br.com.tjro.supribackend.model.ElementoDespesa;
import br.com.tjro.supribackend.repository.ElementoDespesaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ElementoDespesaServiceImplTest {

    @InjectMocks
    ElementoDespesaServiceImpl elementoDespesaService;

    @Mock
    ElementoDespesaRepository elementoDespesaRepository;

    @Mock
    ModelMapper modelMapper;

    @Mock
    Pageable pageable;

    @Test
    void salvarEditarElementoDespesa() {
        ElementoDespesaDto elementoDespesaDto = new ElementoDespesaDto();
        List<ElementoDespesaDto> elementoDespesaDtoList = new ArrayList<>();
        elementoDespesaDtoList.add(elementoDespesaDto);
        ElementoDespesa elementoDespesa = new ElementoDespesa();
        Mockito.when(modelMapper.map(elementoDespesaDto, ElementoDespesa.class)).thenReturn(elementoDespesa);
        this.elementoDespesaService.salvarEditarElementoDespesa(elementoDespesaDtoList, null);
    }

    @Test
    void listarElementoDespesa() {

        List<ElementoDespesa> elementoDespesas = new ArrayList<>();

        ElementoDespesa elementoDespesa = new ElementoDespesa();
        elementoDespesa.setDescricao("descricao");
        elementoDespesas.add(elementoDespesa);

        Mockito.when(this.elementoDespesaRepository.findAllBySolicitacao_Id(pageable, 1L)).thenReturn(new PageImpl<>(elementoDespesas));
        Page<ElementoDespesaDto> elementoDespesaDtos = this.elementoDespesaService.listarElementoDespesa(pageable, 1L);

        Mockito.verify(this.elementoDespesaRepository).findAllBySolicitacao_Id(pageable, 1L);

        Assertions.assertNotNull(elementoDespesaDtos);
    }

    @Test
    void salvarEditarElementoDespesaUnico() {

        ElementoDespesaDto elementoDespesaDto = new ElementoDespesaDto();
        elementoDespesaDto.setDescricao("descricao");
        ElementoDespesa elementoDespesa = new ElementoDespesa();
        elementoDespesa.setDescricao("descricao");

        Mockito.when(modelMapper.map(elementoDespesaDto, ElementoDespesa.class)).thenReturn(elementoDespesa);
        Mockito.when(this.elementoDespesaRepository.save(Mockito.any())).thenReturn(elementoDespesa);

        Mockito.when(modelMapper.map(elementoDespesa, ElementoDespesaDto.class)).thenReturn(elementoDespesaDto);
        ElementoDespesaDto elementoDespesaDtoSaved = this.elementoDespesaService.salvarEditarElementoDespesaUnico(elementoDespesaDto, 1L);

        Mockito.verify(this.elementoDespesaRepository).save(Mockito.any());

        Assertions.assertNotNull(elementoDespesaDtoSaved);
        Assertions.assertEquals("descricao", elementoDespesaDtoSaved.getDescricao());
    }

    //@Test
    void removerElementoDespesa() {
    }

    //@Test
    void buscarElementoDespesaByIdSolicitacao() {
    }

    //@Test
    void findById() {
    }
}