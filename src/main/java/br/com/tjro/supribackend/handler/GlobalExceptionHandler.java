package br.com.tjro.supribackend.handler;

import br.com.tjro.supribackend.Exceptions.*;
import br.com.tjro.supribackend.dto.ErrorObjectDto;
import br.com.tjro.supribackend.dto.ErrorResponseDto;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
        List<ErrorObjectDto> errors = getErrors(ex);
        ErrorResponseDto errorResponse = getErrorResponse(ex, errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CpfCnpjDuplicateException.class)
    protected ResponseEntity<ErrorResponseDto> handleCpfCnpjDuplicate(CpfCnpjDuplicateException ex, WebRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SolicitacaoInexistenteException.class)
    protected ResponseEntity<ErrorResponseDto> handleSolicitacaoInexistenteException(SolicitacaoInexistenteException ex, WebRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FormatoXmlInvalidoException.class)
    protected ResponseEntity<ErrorResponseDto> handleFormatoXmlInvalidoException(FormatoXmlInvalidoException ex, WebRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DatabaseErrorException.class)
    protected ResponseEntity<ErrorResponseDto> handleDatabaseErrorException(DatabaseErrorException ex, WebRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), null);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ErroAoSalvarException.class)
    protected ResponseEntity<ErrorResponseDto> handleErroAoSalvarException(ErroAoSalvarException ex, WebRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), null);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SolicitacaoComDespesaException.class)
    protected ResponseEntity<ErrorResponseDto> handleSolicitacaoComDespesaException(SolicitacaoComDespesaException ex, WebRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FornecedorInexisteException.class)
    protected ResponseEntity<ErrorResponseDto> handleFornecedorInexisteException(FornecedorInexisteException ex, WebRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NumeroDocumentoFiscalDuplicateException.class)
    protected ResponseEntity<ErrorResponseDto> handleNumeroDocumentoFiscalDuplicateException(NumeroDocumentoFiscalDuplicateException ex, WebRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SupridoInexistenteException.class)
    protected ResponseEntity<ErrorResponseDto> handleSupridoInexistenteException(SupridoInexistenteException ex, WebRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SupridoComSolicitacaoException.class)
    protected ResponseEntity<ErrorResponseDto> handleSupridoComSolicitacaoException(SupridoComSolicitacaoException ex, WebRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotaFiscalNotFoundException.class)
    protected ResponseEntity<ErrorResponseDto> handleNotaFiscalNotFoundException(FornecedorNotFoundException ex, WebRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CpfCnpjInvalidoException.class)
    protected ResponseEntity<ErrorResponseDto> handleCpfCnpjInvalidoException(CpfCnpjInvalidoException ex, WebRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FornecedorNotFoundException.class)
    protected ResponseEntity<ErrorResponseDto> handleFornecedorNotFoundException(FornecedorNotFoundException ex, WebRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OcorrenciaNotFoundException.class)
    protected ResponseEntity<ErrorResponseDto> handleOcorrenciaNotFoundException(OcorrenciaNotFoundException ex, WebRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataPrazoFinalException.class)
    protected ResponseEntity<ErrorResponseDto> handleDataPrazoFinalException(DataPrazoFinalException ex, WebRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenInvalidoException.class)
    protected ResponseEntity<ErrorResponseDto> handleTokenInvalidoException(TokenInvalidoException ex, WebRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ErrorOcorrenciaException.class)
    protected ResponseEntity<ErrorResponseDto> handleErrorOcorrenciaException(ErrorOcorrenciaException ex, WebRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponseDto> handleException(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (ex instanceof HttpStatusCodeException httpError) {
            status = HttpStatus.valueOf(httpError.getStatusCode().value());
        }

        ErrorResponseDto errorResponse = new ErrorResponseDto(status.value(), ExceptionUtils.getRootCauseMessage(ex), null);
        return new ResponseEntity<>(errorResponse, status);
    }

    private ErrorResponseDto getErrorResponse(MethodArgumentNotValidException ex, List<ErrorObjectDto> errors) {
        return new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Requisição possui campos inválidos", errors);
    }

    private List<ErrorObjectDto> getErrors(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new ErrorObjectDto(error.getDefaultMessage(), error.getField(), error.getRejectedValue()))
                .collect(Collectors.toList());
    }
}
