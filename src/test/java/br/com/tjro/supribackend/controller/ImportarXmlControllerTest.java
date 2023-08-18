package br.com.tjro.supribackend.controller;

import br.com.tjro.supribackend.Constantes.Constantes;
import br.com.tjro.supribackend.dto.FornecedorDto;
import br.com.tjro.supribackend.dto.NotaFiscalDto;
import br.com.tjro.supribackend.dto.XmlDto;
import br.com.tjro.supribackend.projection.FornecedorProjection;
import br.com.tjro.supribackend.service.FornecedorServiceImpl;
import br.com.tjro.supribackend.service.ImportarXmlService;
import br.com.tjro.supribackend.service.NotaFiscalServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
public class ImportarXmlControllerTest {

    @Mock
    ImportarXmlService importarXmlService;
    @Mock
    MultipartFile arquivo;

    @Mock
    NotaFiscalServiceImpl notaFiscalService;

    @Mock
    FornecedorServiceImpl fornecedorService;

    @Mock
    FornecedorProjection fornecedorProjection;

    @InjectMocks
    ImportarXmlController importarXmlController;

    @Test
    public void importarXml() throws IOException, ParserConfigurationException, SAXException {
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.xml",
                MediaType.MULTIPART_FORM_DATA_VALUE,
                (Constantes.NOTA_FISCAL).getBytes());

        this.importarXmlController.importarXml(file);
        assertDoesNotThrow(() -> importarXmlService.importarXml(arquivo));
        Mockito.verify(this.importarXmlService).importarXml(arquivo);
    }

    @Test
    public void salvarXml() throws Exception {

        XmlDto xmlDto = new XmlDto();
        NotaFiscalDto notaFiscalDto = new NotaFiscalDto();
        notaFiscalDto.setNumeroDocumentoFiscal("numero");
        xmlDto.setNotaFiscalDto(new NotaFiscalDto());
        FornecedorDto fornecedorDto = new FornecedorDto();
        xmlDto.setFornecedorDto(fornecedorDto);
        xmlDto.getFornecedorDto().setCpfCnpj("cpf");
        List<FornecedorProjection> fornecedorDtoList = new ArrayList<>();

        fornecedorDtoList.add(fornecedorProjection);

        Mockito.when(this.fornecedorService.findByCpfCnpj("cpf")).thenReturn(fornecedorDtoList);

        this.importarXmlController.salvarXml(xmlDto, null);
        Mockito.verify(importarXmlService).salvarXml(xmlDto);
    }
}
