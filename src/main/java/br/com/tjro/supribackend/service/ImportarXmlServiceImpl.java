package br.com.tjro.supribackend.service;

import br.com.tjro.supribackend.Exceptions.FormatoXmlInvalidoException;
import br.com.tjro.supribackend.dto.FornecedorDto;
import br.com.tjro.supribackend.dto.ItemNotaFiscalDto;
import br.com.tjro.supribackend.dto.NotaFiscalDto;
import br.com.tjro.supribackend.dto.XmlDto;
import br.com.tjro.supribackend.enums.Status;
import br.com.tjro.supribackend.model.ItemNotaFiscal;
import br.com.tjro.supribackend.model.NotaFiscal;
import br.com.tjro.supribackend.projection.FornecedorProjection;
import io.micrometer.core.instrument.util.IOUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class ImportarXmlServiceImpl  implements  ImportarXmlService {

    @Autowired
    NotaFiscalServiceImpl notaFiscalService;

    @Autowired
    FornecedorServiceImpl fornecedorService;

    @Autowired
    ItemNotaFiscalService itemNotaFiscalService;

    public XmlDto importarXml(MultipartFile arquivo) throws IOException, ParserConfigurationException, SAXException {
        Long tamanho = arquivo.getSize()/1024;
        if (tamanho > 500) {
            throw new IOException();
        }

        String[] arquivoSplited = arquivo.getOriginalFilename().split("\\.");

        String extensao = arquivoSplited.length > 1 ? arquivoSplited[1] : null;

        if (!extensao.contentEquals("xml")) {
            throw new FormatoXmlInvalidoException("Formato inválido");
        }

        try {
            InputStream retorno = arquivo.getInputStream();

            String textoConvertido = IOUtils.toString(retorno);

            InputStream source = new ByteArrayInputStream(textoConvertido.getBytes());
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(source);

            XmlDto xmlDto = new XmlDto();
            xmlDto.setFornecedorDto(this.criarFornecedor(document));
            xmlDto.setNotaFiscalDto(this.criarNotaFiscal(document));
            xmlDto.setItemsNotasFiscaisDtos(this.criarItemNotaFiscal(document));
            return xmlDto;

        }catch (Exception e) {
            log.error("Erro no formato do aquivo: {}", e.getMessage());
            throw new FormatoXmlInvalidoException("Erro no formato do arquivo");

        }
    }

    private FornecedorDto criarFornecedor(Document document) {
        FornecedorDto fornecedorDto = new FornecedorDto();
        NodeList nList = document.getElementsByTagName("infNFe");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                fornecedorDto.setCpfCnpj(eElement.getElementsByTagName("CNPJ").item(0).getTextContent());
            }
        }
        return fornecedorDto;
    }
    private NotaFiscalDto criarNotaFiscal(Document document) {

        NotaFiscal notaFiscal = new NotaFiscal();
        notaFiscal.setDataDocumento(this.criarDataNota(document));
        NodeList nList = document.getElementsByTagName("infNFe");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                notaFiscal.setChaveAcessoNfe(eElement.getAttribute("Id"));
                notaFiscal.setNumeroDocumentoFiscal(eElement.getElementsByTagName("nNF").item(0).getTextContent());
                BigDecimal valorTotal = new BigDecimal(eElement.getElementsByTagName("vNF").item(0).getTextContent());
                BigDecimal frete = new BigDecimal(eElement.getElementsByTagName("vFrete").item(0).getTextContent());
                notaFiscal.setValorTotal(valorTotal.subtract(frete));
            }
        }
        NotaFiscalDto notaFiscalDto = new NotaFiscalDto();
        BeanUtils.copyProperties(notaFiscal, notaFiscalDto);
        return notaFiscalDto;
    }

    private LocalDate criarDataNota(Document document) {
        NodeList dataNList = document.getElementsByTagName("infProt");
        LocalDate date = null;

        for (int temp = 0; temp < dataNList.getLength(); temp++) {
            Node nNode = dataNList.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                String data = eElement.getElementsByTagName("dhRecbto").item(0).getTextContent();
                String[] dataSplit = data.split("T");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                date = LocalDate.parse(dataSplit[0], formatter);
            }
        }

        return date;
    }
    private List<ItemNotaFiscalDto> criarItemNotaFiscal(Document document) {
        List<ItemNotaFiscalDto> itemsDto = new ArrayList<>();

        NodeList itemList = document.getElementsByTagName("prod");

        for (int temp = 0; temp < itemList.getLength(); temp++) {
            Node nNode = itemList.item(temp);
            ItemNotaFiscal itemNotaFiscal = new ItemNotaFiscal();

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                itemNotaFiscal.setDescricao(eElement.getElementsByTagName("xProd").item(0).getTextContent());
                BigDecimal qtd = new BigDecimal(eElement.getElementsByTagName("qCom").item(0).getTextContent());
                itemNotaFiscal.setQuantidade(qtd);
                BigDecimal desc = BigDecimal.valueOf(0.00);
                if (eElement.getElementsByTagName("vDesc").getLength() > 0) {
                    desc = new BigDecimal(eElement.getElementsByTagName("vDesc").item(0).getTextContent());
                    itemNotaFiscal.setDesconto(desc);
                }
                BigDecimal valorUnit = new BigDecimal(eElement.getElementsByTagName("vUnTrib").item(0).getTextContent());
                itemNotaFiscal.setValorUnitario(valorUnit);

                BigDecimal total = (valorUnit.multiply(qtd)).subtract(desc);
                itemNotaFiscal.setValorTotal(total);
            }
            ItemNotaFiscalDto notaFiscalDto = new ItemNotaFiscalDto();
            BeanUtils.copyProperties(itemNotaFiscal, notaFiscalDto);

            itemsDto.add(notaFiscalDto);
        }
        return itemsDto;
    }

    @Override
    public XmlDto salvarXml(XmlDto xmlDto) throws Exception {

        FornecedorProjection fornecedorProjection = this.criarFornecedorXml(xmlDto).get(0);
        Long idFornecedor = fornecedorProjection.getIdFornecedor();

        NotaFiscalDto notaFiscalSavedDto = this.criarNotaFiscalXml(xmlDto, idFornecedor);
        Long idNotaFiscal = notaFiscalSavedDto.getIdNotaFiscal();

        List<ItemNotaFiscalDto> itemNotaFiscalsResponseDto = this.criarItemNotaFiscalXml(xmlDto, idNotaFiscal);

        XmlDto xmlDtoResponse = new XmlDto();
        xmlDtoResponse.setNotaFiscalDto(notaFiscalSavedDto);
        FornecedorDto fornecedorDto = new FornecedorDto();
        BeanUtils.copyProperties(fornecedorProjection, fornecedorDto);
        xmlDtoResponse.setFornecedorDto(fornecedorDto);
        xmlDtoResponse.setItemsNotasFiscaisDtos(itemNotaFiscalsResponseDto);

        return xmlDtoResponse;
    }

    @Override
    @Transactional
    public void removeNotaEItensFiscais(String numeroDocumentoFiscal) {
        List<NotaFiscalDto> notasFiscais = this.notaFiscalService.findByNumeroDocumentoFiscal(numeroDocumentoFiscal);
        Long idNotaFiscal = notasFiscais.get(0).getIdNotaFiscal();

        this.itemNotaFiscalService.removerItemNotaFiscalByIdNotaFiscal(idNotaFiscal);

        this.notaFiscalService.removerNotaFiscal(idNotaFiscal);
    }

    private List<FornecedorProjection> criarFornecedorXml(XmlDto xmlDto) throws Exception {

        List<FornecedorProjection> fornecedorProjectionList = this.fornecedorService.findByCpfCnpj(xmlDto.getFornecedorDto().getCpfCnpj());

        return fornecedorProjectionList;
    }

    private NotaFiscalDto criarNotaFiscalXml(XmlDto xmlDto, Long idFornecedor) {
        NotaFiscalDto notaFiscalDto = new NotaFiscalDto();
        notaFiscalDto.setIdFornecedor(idFornecedor);
        notaFiscalDto.setNumeroDocumentoFiscal(xmlDto.getNotaFiscalDto().getNumeroDocumentoFiscal());
        notaFiscalDto.setNumeroProcessoSei(xmlDto.getNotaFiscalDto().getNumeroProcessoSei());
        notaFiscalDto.setNomeSuprido(xmlDto.getNotaFiscalDto().getNomeSuprido());
        notaFiscalDto.setElementoDespesa(xmlDto.getNotaFiscalDto().getElementoDespesa());
        notaFiscalDto.setTipoDocumentoFiscal(xmlDto.getNotaFiscalDto().getTipoDocumentoFiscal());
        notaFiscalDto.setDataAplicacao(xmlDto.getNotaFiscalDto().getDataAplicacao());
        notaFiscalDto.setDataDocumento(xmlDto.getNotaFiscalDto().getDataDocumento());
        notaFiscalDto.setChaveAcessoNfe(xmlDto.getNotaFiscalDto().getChaveAcessoNfe());
        notaFiscalDto.setValorTotal(xmlDto.getNotaFiscalDto().getValorTotal());
        notaFiscalDto.setStatus(Status.ATIVO);

        NotaFiscalDto notaFiscalSavedDto = this.notaFiscalService.salvarEditarNotaFiscal(notaFiscalDto, null);

        return notaFiscalSavedDto;
    }

    private List<ItemNotaFiscalDto> criarItemNotaFiscalXml(XmlDto xmlDto, Long idNotaFiscal) {
        List<ItemNotaFiscalDto> itemNotaFiscalDto = xmlDto.getItemsNotasFiscaisDtos();

        itemNotaFiscalDto.stream().forEach(item -> {
            item.setIdNotaFiscal(idNotaFiscal);
            this.itemNotaFiscalService.salvarEditarItemNotaFiscal(item, null);
        });

        return itemNotaFiscalDto;
    }
}
