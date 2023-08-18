package br.com.tjro.supribackend.service;

import br.com.tjro.supribackend.dto.ItemNotaFiscalDto;
import br.com.tjro.supribackend.model.ItemNotaFiscal;
import br.com.tjro.supribackend.model.NotaFiscal;
import br.com.tjro.supribackend.repository.ItemNotaFiscalRepository;
import br.com.tjro.supribackend.repository.NotaFiscalRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemNotaFiscalServiceImpl implements ItemNotaFiscalService {

    @Autowired
    private final ItemNotaFiscalRepository itemNotaFiscalRepository;
    @Autowired
    private final NotaFiscalRepository notaFiscalRepository;

    @Override
    @Transactional
    public ItemNotaFiscalDto salvarEditarItemNotaFiscal(ItemNotaFiscalDto itemNotaFiscalDto, Long idItemNotaFiscal) {
        ItemNotaFiscal itemNotaFiscal = new ItemNotaFiscal();
        BeanUtils.copyProperties(itemNotaFiscalDto, itemNotaFiscal);

        if (idItemNotaFiscal != null) {
            itemNotaFiscal.setIdItemNotaFiscal(idItemNotaFiscal);
        }

        NotaFiscal notaFiscal = notaFiscalRepository.findById(itemNotaFiscalDto.getIdNotaFiscal()).orElse(null);

        if (notaFiscal != null) {
            itemNotaFiscal.setNotaFiscal(notaFiscal);
            itemNotaFiscalRepository.save(itemNotaFiscal);
        }
        ItemNotaFiscalDto itemNotaFiscalDtoSaved = new ItemNotaFiscalDto();
        BeanUtils.copyProperties(itemNotaFiscal, itemNotaFiscalDtoSaved);

        return itemNotaFiscalDtoSaved;
    }

    @Override
    public ItemNotaFiscalDto buscarItemNotaFiscal(Long id) {
        ItemNotaFiscal itemNotaFiscal = itemNotaFiscalRepository.findById(id).orElse(null);
        ItemNotaFiscalDto itemNotaFiscalDto = new ItemNotaFiscalDto();
        BeanUtils.copyProperties(itemNotaFiscal, itemNotaFiscalDto);
        itemNotaFiscalDto.setIdNotaFiscal(itemNotaFiscal.getNotaFiscal().getIdNotaFiscal());

        return itemNotaFiscalDto;
    }

    @Override
    @Transactional
    public void removerItemNotaFiscal(Long id) {
        ItemNotaFiscalDto itemNotaFiscalDto = this.buscarItemNotaFiscal(id);
        ItemNotaFiscal itemNotaFiscal = new ItemNotaFiscal();
        BeanUtils.copyProperties(itemNotaFiscalDto, itemNotaFiscal);
        itemNotaFiscalRepository.delete(itemNotaFiscal);
    }

    @Override
    public List<ItemNotaFiscalDto> findAllItemsNotaFiscal(Long id) {

        List<ItemNotaFiscalDto> itensNotasDto = new ArrayList<>();
        List<ItemNotaFiscal> itemNotaFiscal = itemNotaFiscalRepository.findAll(id);
        itemNotaFiscal.stream().forEach(nota -> {
            ItemNotaFiscalDto notaFiscalDto = new ItemNotaFiscalDto();
            BeanUtils.copyProperties(nota, notaFiscalDto);

            itensNotasDto.add(notaFiscalDto);
        });

        return itensNotasDto;
    }

    @Override
    public void removerItemNotaFiscalByIdNotaFiscal(Long idNotaFiscal) {
        this.itemNotaFiscalRepository.deleteItensNotasFiscaisByIdNotaFiscal(idNotaFiscal);
    }
}
