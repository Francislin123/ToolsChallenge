package com.bank.payments.adapters.in.request;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
public class TransacaoRequest {

    private UUID id;
    private String cartao;
    private DescricaoRequest descricao;
    private FormaPagamentoRequest formaPagamento;

    @Builder
    public TransacaoRequest(String cartao, UUID id, DescricaoRequest descricao, FormaPagamentoRequest formaPagamento) {
        this.cartao = cartao;
        this.id = id;
        this.descricao = descricao;
        this.formaPagamento = formaPagamento;
    }
}