package br.com.tjro.supribackend.controller;

import br.com.tjro.supribackend.Exceptions.FornecedorInexisteException;
import br.com.tjro.supribackend.Exceptions.NumeroDocumentoFiscalDuplicateException;
import br.com.tjro.supribackend.dto.ResponseInsertDto;
import br.com.tjro.supribackend.dto.XmlDto;
import br.com.tjro.supribackend.service.FornecedorServiceImpl;
import br.com.tjro.supribackend.service.ImportarXmlService;
import br.com.tjro.supribackend.service.NotaFiscalServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/xml")
public class ImportarXmlController {
    @Autowired
    ImportarXmlService importarXmlService;
    @Autowired
    NotaFiscalServiceImpl notaFiscalService;
    @Autowired
    FornecedorServiceImpl fornecedorService;


    @PostMapping("/importar")
    public ResponseEntity importarXml(@RequestParam MultipartFile arquivo) throws IOException, ParserConfigurationException, SAXException {

            XmlDto xmlDto= this.importarXmlService.importarXml(arquivo);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(xmlDto != null ? xmlDto : null);
    }

    @PostMapping
    public ResponseEntity<ResponseInsertDto> salvarXml(@Valid @RequestBody XmlDto xmlDto, @RequestParam Boolean reescrever) throws Exception {

        if (this.notaFiscalService.findByNumeroDocumentoFiscal(xmlDto.getNotaFiscalDto().getNumeroDocumentoFiscal()).size() > 0 && !reescrever) {
            throw new NumeroDocumentoFiscalDuplicateException("Nota fiscal já existe");
        }

        if (this.fornecedorService.findByCpfCnpj(xmlDto.getFornecedorDto().getCpfCnpj()).size() == 0) {
            throw new FornecedorInexisteException("Fornecedor Inexistente");
        }

        if (this.notaFiscalService.findByNumeroDocumentoFiscal(xmlDto.getNotaFiscalDto().getNumeroDocumentoFiscal()).size() > 0 && reescrever) {

            this.importarXmlService.removeNotaEItensFiscais(xmlDto.getNotaFiscalDto().getNumeroDocumentoFiscal());
        }

        XmlDto save = importarXmlService.salvarXml(xmlDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseInsertDto(save != null ? save.getNotaFiscalDto().getIdNotaFiscal() : null));
    }
}
