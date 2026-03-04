package com.bank.payments.adapters.in.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DescricaoRequest {

    private Double valor;
    private LocalDateTime dataHora;
    private String estabelecimento;

    // Dados para Response
    private String nsu;
    private String codigoAutorizacao;
    private String status;

    @Builder
    public DescricaoRequest(Double valor, LocalDateTime dataHora, String estabelecimento,
                            String nsu, String codigoAutorizacao, String status) {
        this.valor = valor;
        this.dataHora = dataHora;
        this.estabelecimento = estabelecimento;
        this.nsu = nsu;
        this.codigoAutorizacao = codigoAutorizacao;
        this.status = status;
    }
}