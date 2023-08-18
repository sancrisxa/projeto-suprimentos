package br.com.tjro.supribackend.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ValidaCpfCnpjException {

    @InjectMocks
    ValidaCpfCnpj validaCpfCnpj;

    @Test
    @DisplayName("Deve retornar um cpf valido")
    public void isCPFTest() {
        boolean cpf = ValidaCpfCnpj.isCPF("16616587096");

        Assertions.assertEquals(true, cpf);
    }
    @Test
    @DisplayName("Deve retornar um cpf valido")
    public void invalidCPFTest() {
        boolean cpf = ValidaCpfCnpj.isCPF("16616587099");

        Assertions.assertEquals(false, cpf);
    }

    @Test
    @DisplayName("Deve retornar um cnpj valido")
    public void isCNPJTest() {
        boolean cnpj = ValidaCpfCnpj.isCNPJ("62290149000100");

        Assertions.assertEquals(true, cnpj);
    }

    @Test
    @DisplayName("Deve retornar um cnpj invalido")
    public void invalidCNPJTest() {
        boolean cnpj = ValidaCpfCnpj.isCNPJ("62290149000101");

        Assertions.assertEquals(false, cnpj);
    }
}
