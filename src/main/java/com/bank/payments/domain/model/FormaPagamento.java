package com.bank.payments.domain.model;

import com.bank.payments.domain.enums.FormaPagamentoEnum;
import lombok.Builder;
import lombok.Data;

@Data
public class FormaPagamento {

    private FormaPagamentoEnum tipo;  // AVISTA, PARCELADO_LOJA, PARCELADO_EMISSOR
    private Integer parcelas;

    @Builder
    public FormaPagamento(FormaPagamentoEnum tipo, Integer parcelas) {
        this.tipo = tipo;
        this.parcelas = parcelas;
    }
}
