package com.bank.payments.adapters.in;

import com.bank.payments.adapters.in.request.TransacaoDTORequest;
import com.bank.payments.domain.model.Transacao;
import com.bank.payments.domain.ports.useCase.PaymentUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentUseCase paymentUseCase;

    @PostMapping
    public Transacao create(@Valid @RequestBody TransacaoDTORequest transacaoRequest) {
        return paymentUseCase.createPayment(transacaoRequest);
    }

    @GetMapping
    public List<Transacao> getAll() {
        return paymentUseCase.getAll();
    }

    @GetMapping("/{id}")
    public Transacao getById(@PathVariable UUID id) {
        return paymentUseCase.getById(id);
    }

    @PostMapping("/{id}/refund")
    public Transacao refund(@PathVariable UUID id) {
        return paymentUseCase.refund(id);
    }
}
