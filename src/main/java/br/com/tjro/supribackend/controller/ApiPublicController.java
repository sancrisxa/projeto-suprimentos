package br.com.tjro.supribackend.controller;

import br.com.tjro.supribackend.dto.ItemNotaFiscalDto;
import br.com.tjro.supribackend.dto.NotaComItemPublicaDto;
import br.com.tjro.supribackend.projection.NotaApiPublicProjection;
import br.com.tjro.supribackend.service.ApiPublicService;
import br.com.tjro.supribackend.service.ItemNotaFiscalService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/transparencia")
public class ApiPublicController {

    @Autowired
    private ApiPublicService apiPublicService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ItemNotaFiscalService itemNotaFiscalService;

    @GetMapping
    public ResponseEntity<Page<NotaComItemPublicaDto>> buscarNotaPorAnoMes(Pageable pageable,
                                                                           @RequestParam(value = "ano", required = false) String ano,
                                                                           @RequestParam(value = "mes", required = false) String mes
    ) {
        Page<NotaApiPublicProjection> notas = this.apiPublicService.buscarNotaPorAnoMes(pageable,
                ano,
                mes);


        Page<NotaComItemPublicaDto> notaComItemPublicaDtoPage = notas.map(nota -> {
            NotaComItemPublicaDto notaComItemPublicaDto;
            notaComItemPublicaDto = modelMapper.map(nota, NotaComItemPublicaDto.class);
            List<ItemNotaFiscalDto> allItemsNotaFiscal = this.itemNotaFiscalService.findAllItemsNotaFiscal(nota.getIdNotaFiscal());
            notaComItemPublicaDto.setItemNotaFiscal(allItemsNotaFiscal.stream().map(item -> {
                item.setIdNotaFiscal(nota.getIdNotaFiscal());

                return item;
            }).collect(Collectors.toList()));
            return notaComItemPublicaDto;
        });

        return ResponseEntity.ok(notaComItemPublicaDtoPage);
    }
}
