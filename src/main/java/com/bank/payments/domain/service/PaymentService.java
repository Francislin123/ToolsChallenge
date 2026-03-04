package com.bank.payments.domain.service;

import com.bank.payments.adapters.in.mapper.TransacaoMapper;
import com.bank.payments.adapters.in.request.TransacaoDTORequest;
import com.bank.payments.domain.exception.PaymentNotFoundException;
import com.bank.payments.domain.model.Transacao;
import com.bank.payments.domain.ports.repository.PaymentRepositoryPort;
import com.bank.payments.domain.ports.useCase.PaymentUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService implements PaymentUseCase {

    private PaymentRepositoryPort repository;

    @Override
    public Transacao createPayment(TransacaoDTORequest transacaoRequest) {
        log.info("Starting payment creation process");
        Transacao transacao = TransacaoMapper.toDomain(transacaoRequest);
        log.debug("Payment built with ID: {}", transacao.getId());
        Transacao saved = repository.save(transacao);
        log.info("Payment successfully saved with ID: {}", saved.getId());
        return saved;
    }

    @Override
    public List<Transacao> getAll() {
        log.info("Fetching all payments");
        List<Transacao> payments = repository.findAll();
        log.debug("Total payments found: {}", payments.size());
        return payments;
    }

    @Override
    public Transacao getById(UUID id) {
        log.info("Fetching payment by ID: {}", id);
        return repository.findById(id)
                .map(payment -> {
                    log.debug("Payment found: {}", payment.getId());
                    return payment;
                })
                .orElseThrow(() -> {
                    log.error("Payment not found for ID: {}", id);
                    return new PaymentNotFoundException("Payment not found");
                });
    }

    @Override
    public Transacao refund(UUID id) {
        log.info("Starting refund process for payment ID: {}", id);
        Transacao payment = getById(id);
        log.debug("Current payment status before refund: {}",
                payment.getDescricao().getStatus());
        payment.refund();
        Transacao refunded = repository.save(payment);
        log.info("Refund completed successfully for payment ID: {}", id);
        return refunded;
    }
}