package com.bank.payments.adapters.in.mapper;

import com.bank.payments.adapters.in.request.TransacaoDTORequest;
import com.bank.payments.domain.model.Descricao;
import com.bank.payments.domain.model.FormaPagamento;
import com.bank.payments.domain.model.Transacao;

import java.time.LocalDateTime;

public class TransacaoMapper {

    private TransacaoMapper() {
        // evita instanciamento
    }

    public static Transacao toDomain(TransacaoDTORequest request) {
        return Transacao.builder()
                .cartao(request.getTransacao().getCartao())
                .id(request.getTransacao().getId())
                .descricao(
                        Descricao.builder()
                                .valor(request.getTransacao().getDescricao().getValor())
                                .dataHora(LocalDateTime.now())
                                .estabelecimento(request.getTransacao().getDescricao().getEstabelecimento())
                                .build()
                )
                .formaPagamento(
                        FormaPagamento.builder()
                                .tipo(request.getTransacao().getFormaPagamento().getTipo())
                                .parcelas(request.getTransacao().getFormaPagamento().getParcelas())
                                .build()
                )
                .build();
    }
}