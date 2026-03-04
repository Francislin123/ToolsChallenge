package com.bank.payments.adapters.in.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoDTORequest {
    private TransacaoRequest transacao;
}
