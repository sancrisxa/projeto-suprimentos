package br.com.tjro.supribackend.service;

import br.com.tjro.supribackend.dto.NotaComItemDto;
import br.com.tjro.supribackend.projection.NotaProjection;
import br.com.tjro.supribackend.repository.NotaFiscalRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ApiServiceImpl implements ApiService {
    @Autowired
    private NotaFiscalRepository notaFiscalRepository;
    @Autowired
    private ItemNotaFiscalService itemNotaFiscalService;
    @Override
    public List<NotaComItemDto> buscarNotaPorData(Pageable pageable, LocalDate dataInicial, LocalDate dataFinal) {

        List<NotaComItemDto> notaComItemDtos = new ArrayList<>();

        List<NotaProjection> notas = this.notaFiscalRepository.buscarNotaPorData(pageable, dataInicial == null ? null : dataInicial, dataFinal == null ? null : dataFinal).get().toList();

        for (int temp = 0; temp < notas.size(); temp++) {
            NotaComItemDto notaComItemDto = new NotaComItemDto();
            notaComItemDto.setMatriculaSuprido(notas.get(temp).getMatriculaSuprido());
            notaComItemDto.setNumeroProcessoSei(notas.get(temp).getNumeroProcessoSei());
            notaComItemDto.setNomeSuprido(notas.get(temp).getNomeSuprido());
            notaComItemDto.setChaveAcessoNfe(notas.get(temp).getChaveAcessoNfe());
            notaComItemDto.setElementoDespesa(notas.get(temp).getElementoDespesa());
            notaComItemDto.setTipoDocumentoFiscal(notas.get(temp).getTipoDocumentoFiscal());
            notaComItemDto.setNumeroDocumentoFiscal(notas.get(temp).getNumeroDocumentoFiscal());
            notaComItemDto.setValorTotal(notas.get(temp).getValorTotal());
            notaComItemDto.setDataDocumento(notas.get(temp).getDataDocumento());
            notaComItemDto.setDataAplicacao(notas.get(temp).getDataAplicacao());
            notaComItemDto.setRazaoSocialFornecedor(notas.get(temp).getRazaoSocial());

            notaComItemDto.setItemNotaFiscal(this.itemNotaFiscalService.findAllItemsNotaFiscal(notas.get(temp).getIdNotaFiscal()));
            notaComItemDtos.add(notaComItemDto);
        }

        return notaComItemDtos;
    }
}
