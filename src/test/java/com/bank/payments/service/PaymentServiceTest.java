package com.bank.payments.service;

import com.bank.payments.adapters.in.request.DescricaoRequest;
import com.bank.payments.adapters.in.request.FormaPagamentoRequest;
import com.bank.payments.adapters.in.request.TransacaoDTORequest;
import com.bank.payments.adapters.in.request.TransacaoRequest;
import com.bank.payments.domain.enums.FormaPagamentoEnum;
import com.bank.payments.domain.enums.Status;
import com.bank.payments.domain.exception.RefundNotAllowedException;
import com.bank.payments.domain.model.Descricao;
import com.bank.payments.domain.model.FormaPagamento;
import com.bank.payments.domain.model.Transacao;
import com.bank.payments.domain.ports.repository.PaymentRepositoryPort;
import com.bank.payments.domain.ports.useCase.PaymentUseCase;
import com.bank.payments.domain.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceTest {

    @Mock
    private PaymentRepositoryPort repository;

    @InjectMocks
    private PaymentUseCase service = new PaymentService();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreatePaymentSuccessfully() {

        UUID id = UUID.randomUUID();

        DescricaoRequest descricaoRequest = DescricaoRequest.builder()
                .valor(150.0)
                .dataHora(LocalDateTime.now())
                .estabelecimento("Loja Teste")
                .nsu("123456789012")
                .codigoAutorizacao("654321")
                .status("AUTORIZADO")
                .build();

        FormaPagamentoRequest formaPagamento = FormaPagamentoRequest.builder()
                .tipo(FormaPagamentoEnum.valueOf(FormaPagamentoEnum.AVISTA.name()))
                .parcelas(1)
                .build();

        TransacaoRequest transacaoRequest = TransacaoRequest.builder()
                .id(id)
                .cartao("1234567890123456")
                .descricao(descricaoRequest)
                .formaPagamento(formaPagamento)
                .build();

        TransacaoDTORequest transacaoDTORequest = TransacaoDTORequest.builder().transacao(transacaoRequest).build();

        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Transacao result = service.createPayment(transacaoDTORequest);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(repository, times(1)).save(any());
    }

    @Test
    void shouldReturnAllPayments() {

        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<Transacao> result = service.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Test
    void shouldReturnPaymentById() {

        UUID id = UUID.randomUUID();
        Transacao transacao = mock(Transacao.class);

        when(repository.findById(id)).thenReturn(Optional.of(transacao));

        Transacao result = service.getById(id);

        assertNotNull(result);
        verify(repository).findById(id);
    }

    @Test
    void shouldThrowExceptionWhenPaymentNotFound() {

        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(RuntimeException.class, () -> service.getById(id));

        assertEquals("Payment not found", exception.getMessage());
        verify(repository).findById(id);
    }

    @Test
    void shouldRefundSuccessfully() {

        UUID id = UUID.randomUUID();

        Descricao descricao = Descricao.builder()
                .valor(100.0)
                .dataHora(LocalDateTime.now())
                .estabelecimento("Loja Teste")
                .status(Status.AUTORIZADO.name())
                .build();

        Transacao transacao = Transacao.builder()
                .id(id)
                .cartao("123")
                .descricao(descricao)
                .formaPagamento(FormaPagamento.builder().tipo(FormaPagamentoEnum.valueOf(FormaPagamentoEnum.AVISTA.name())).parcelas(1).build())
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(transacao));
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Transacao result = service.refund(id);

        assertEquals(Status.ESTORNADO.name(), result.getDescricao().getStatus());
        verify(repository).save(transacao);
    }

    @Test
    void shouldThrowExceptionWhenRefundNotAllowed() {

        UUID id = UUID.randomUUID();

        Descricao descricao = Descricao.builder()
                .valor(100.0)
                .dataHora(LocalDateTime.now())
                .estabelecimento("Loja Teste")
                .status(Status.NEGADO.name())
                .build();

        Transacao transacao = Transacao.builder()
                .id(id)
                .cartao("123")
                .descricao(descricao)
                .formaPagamento(FormaPagamento.builder()
                        .tipo(FormaPagamentoEnum.valueOf(FormaPagamentoEnum.AVISTA.name()))
                        .parcelas(1).
                        build())
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(transacao));

        assertThrows(RefundNotAllowedException.class, () -> service.refund(id));
    }
}
