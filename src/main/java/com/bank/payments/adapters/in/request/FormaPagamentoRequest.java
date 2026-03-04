package com.bank.payments.adapters.in.request;

import com.bank.payments.domain.enums.FormaPagamentoEnum;
import lombok.Builder;
import lombok.Data;

@Data
public class FormaPagamentoRequest {

    private FormaPagamentoEnum tipo;  // AVISTA, PARCELADO_LOJA, PARCELADO_EMISSOR
    private Integer parcelas;

    @Builder
    public FormaPagamentoRequest(FormaPagamentoEnum tipo, Integer parcelas) {
        this.tipo = tipo;
        this.parcelas = parcelas;
    }
}
