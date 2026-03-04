package com.bank.payments.domain.model;

import com.bank.payments.domain.enums.Status;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Data
@Slf4j
public class Descricao {

    private Double valor;
    private LocalDateTime dataHora;
    private String estabelecimento;

    // Dados de retorno
    private String nsu;
    private String codigoAutorizacao;
    private String status;

    @Builder
    public Descricao(Double valor, LocalDateTime dataHora, String estabelecimento,
                     String nsu, String codigoAutorizacao, String status) {

        log.info("Iniciando criação da Descricao para estabelecimento: {}", estabelecimento);

        this.valor = valor;
        this.dataHora = dataHora;
        this.estabelecimento = estabelecimento;

        this.nsu = (nsu != null) ? nsu : gerarNsu();
        this.codigoAutorizacao = (codigoAutorizacao != null) ? codigoAutorizacao : gerarCodigoAutorizacao();
        this.status = (status != null) ? status : definirStatus();

        log.info("Descricao criada com NSU: {} e Status: {}", this.nsu, this.status);
    }

    private String gerarNsu() {
        String generated = UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 12);

        log.debug("NSU gerado automaticamente: {}", generated);
        return generated;
    }

    private String gerarCodigoAutorizacao() {
        Random random = new Random();
        int codigo = 100000 + random.nextInt(900000);
        String generated = String.valueOf(codigo);

        log.debug("Código de autorização gerado: {}", generated);
        return generated;
    }

    private String definirStatus() {
        if (this.valor != null && this.valor > 0) {
            log.debug("Pagamento autorizado para valor: {}", this.valor);
            return Status.AUTORIZADO.name();
        }

        log.warn("Pagamento negado. Valor inválido: {}", this.valor);
        return Status.NEGADO.name();
    }
}