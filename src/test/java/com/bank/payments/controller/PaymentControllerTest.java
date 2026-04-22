package com.bank.payments.controller;

import com.bank.payments.adapters.in.PaymentController;
import com.bank.payments.domain.model.Transacao;
import com.bank.payments.domain.ports.useCase.PaymentUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentUseCase service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreatePayment() throws Exception {

        Transacao transacao = Mockito.mock(Transacao.class);

        Mockito.when(service.createPayment(any())).thenReturn(transacao);

        String json = """
        {
          "transacao": {
            "cartao": "1234567890123456",
            "id": "550e8400-e29b-41d4-a716-446655440000",
            "descricao": {
              "valor": 100.0,
              "estabelecimento": "Loja Teste"
            },
            "formaPagamento": {
              "tipo": "AVISTA",
              "parcelas": 1
            }
          }
        }
        """;

        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnAllPayments() throws Exception {

        Mockito.when(service.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/payments"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnPaymentById() throws Exception {

        UUID id = UUID.randomUUID();
        Transacao transacao = Mockito.mock(Transacao.class);

        Mockito.when(service.getById(id)).thenReturn(transacao);

        mockMvc.perform(get("/payments/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRefundPayment() throws Exception {

        UUID id = UUID.randomUUID();
        Transacao transacao = Mockito.mock(Transacao.class);

        Mockito.when(service.refund(id)).thenReturn(transacao);

        mockMvc.perform(post("/payments/{id}/refund", id))
                .andExpect(status().isOk());
    }
}
