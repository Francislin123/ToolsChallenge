package com.bank.payments.domain.model;

import com.bank.payments.domain.enums.Status;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DescricaoTest {

    @Test
    void shouldGenerateNsuAndAuthorizationCodeAutomatically() {

        Descricao descricao = Descricao.builder()
                .valor(100.0)
                .dataHora(LocalDateTime.now())
                .estabelecimento("Loja Teste")
                .build();

        assertNotNull(descricao.getNsu());
        assertEquals(12, descricao.getNsu().length());

        assertNotNull(descricao.getCodigoAutorizacao());
        assertEquals(6, descricao.getCodigoAutorizacao().length());

        assertEquals(Status.AUTORIZADO.name(), descricao.getStatus());
    }

    @Test
    void shouldSetStatusToAuthorizedWhenValueIsGreaterThanZero() {

        Descricao descricao = Descricao.builder()
                .valor(50.0)
                .dataHora(LocalDateTime.now())
                .estabelecimento("Loja Teste")
                .build();

        assertEquals(Status.AUTORIZADO.name(), descricao.getStatus());
    }

    @Test
    void shouldSetStatusToNegadoWhenValueIsZero() {

        Descricao descricao = Descricao.builder()
                .valor(0.0)
                .dataHora(LocalDateTime.now())
                .estabelecimento("Loja Teste")
                .build();

        assertEquals(Status.NEGADO.name(), descricao.getStatus());
    }

    @Test
    void shouldSetStatusToNegadoWhenValueIsNull() {

        Descricao descricao = Descricao.builder()
                .valor(null)
                .dataHora(LocalDateTime.now())
                .estabelecimento("Loja Teste")
                .build();

        assertEquals(Status.NEGADO.name(), descricao.getStatus());
    }

    @Test
    void shouldUseProvidedNsuAndAuthorizationCodeWhenGiven() {

        Descricao descricao = Descricao.builder()
                .valor(100.0)
                .dataHora(LocalDateTime.now())
                .estabelecimento("Loja Teste")
                .nsu("123456789012")
                .codigoAutorizacao("654321")
                .status(Status.AUTORIZADO.name())
                .build();

        assertEquals("123456789012", descricao.getNsu());
        assertEquals("654321", descricao.getCodigoAutorizacao());
        assertEquals(Status.AUTORIZADO.name(), descricao.getStatus());
    }
}