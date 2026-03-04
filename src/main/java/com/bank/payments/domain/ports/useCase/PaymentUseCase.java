package com.bank.payments.domain.ports.useCase;

import com.bank.payments.adapters.in.request.TransacaoDTORequest;
import com.bank.payments.domain.model.Transacao;

import java.util.List;
import java.util.UUID;

public interface PaymentUseCase {

    Transacao createPayment(TransacaoDTORequest transacaoRequest);

    List<Transacao> getAll();

    Transacao getById(UUID id);

    Transacao refund(UUID id);
}
