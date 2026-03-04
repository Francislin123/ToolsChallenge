package com.bank.payments.domain.model;

import com.bank.payments.domain.enums.Status;
import com.bank.payments.domain.exception.RefundNotAllowedException;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.UUID;

@Data
@Slf4j
public class Transacao {

    private UUID id;
    private String cartao;
    private Descricao descricao;
    private FormaPagamento formaPagamento;

    @Builder
    public Transacao(String cartao, UUID id, Descricao descricao, FormaPagamento formaPagamento) {
        log.info("Criando transação [id={}]", id);

        this.cartao = cartao;
        this.id = id;
        this.descricao = descricao;
        this.formaPagamento = formaPagamento;

        log.debug("Transação criada com status inicial: {}",
                descricao != null ? descricao.getStatus() : "N/A");
    }

    public void refund() {
        log.info("Iniciando estorno da transação [id={}]", this.id);
        String statusAtual = this.descricao.getStatus();
        log.debug("Status atual da transação [id={}]: {}", this.id, statusAtual);
        if (!Objects.equals(statusAtual, Status.AUTORIZADO.name())) {
            log.warn(
                    "Estorno não permitido para transação [id={}]. Status atual: {}",
                    this.id, statusAtual
            );
            throw new RefundNotAllowedException(
                    String.format("Refund not allowed for transaction %s. Current status: %s",
                            this.id, statusAtual)
            );
        }
        this.descricao.setStatus(Status.ESTORNADO.name());
        log.info(
                "Transação [id={}] estornada com sucesso. Novo status: {}",
                this.id, this.descricao.getStatus()
        );
    }
}