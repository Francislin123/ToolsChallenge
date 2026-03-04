
package com.bank.payments.adapters.out;

import com.bank.payments.domain.model.Transacao;
import com.bank.payments.domain.ports.repository.PaymentRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryPaymentRepository implements PaymentRepositoryPort {

    private final Map<UUID, Transacao> db = new ConcurrentHashMap<>();

    public Transacao save(Transacao payment) {
        db.put(payment.getId(), payment);
        return payment;
    }

    public Optional<Transacao> findById(UUID id) {
        return Optional.ofNullable(db.get(id));
    }

    public List<Transacao> findAll() {
        return new ArrayList<>(db.values());
    }
}
