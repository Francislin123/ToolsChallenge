package com.bank.payments.domain.ports.repository;

import com.bank.payments.domain.model.Transacao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepositoryPort {
    Transacao save(Transacao payment);
    Optional<Transacao> findById(UUID id);
    List<Transacao> findAll();
}
