package br.com.tjro.supribackend.service;

import br.com.tjro.supribackend.dto.XmlDto;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface ImportarXmlService {
    XmlDto importarXml(MultipartFile arquivo) throws IOException, ParserConfigurationException, SAXException;
    XmlDto salvarXml(XmlDto xmlDto) throws Exception;
    void removeNotaEItensFiscais(String numeroDocumentoFiscal);
}
