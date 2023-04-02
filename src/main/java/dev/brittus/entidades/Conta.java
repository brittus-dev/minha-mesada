package dev.brittus.entidades;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Conta {
    private final String numero;
    private BigDecimal saldo;
    private final List<Transacao> transacoes;

    private Conta(Builder builder) {
        this.numero = builder.numero;
        this.saldo = builder.saldo;
        this.transacoes = builder.transacoes;
    }

    public String getNumero() {
        return numero;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    public void depositar(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor inválido para depósito");
        }
        this.saldo = this.saldo.add(valor);
        Transacao transacao = new Transacao.Builder()
                .tipo(Transacao.TipoTransacao.ENTRADA)
                .valor(valor)
                .data(LocalDateTime.now())
                .descricao("Depósito em dinheiro")
                .build();
        transacoes.add(transacao);
    }

    public void sacar(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor inválido para saque");
        }
        if (valor.compareTo(saldo) > 0) {
            throw new IllegalArgumentException("Saldo insuficiente para saque");
        }
        this.saldo = this.saldo.subtract(valor);
        Transacao transacao = new Transacao.Builder()
                .tipo(Transacao.TipoTransacao.SAIDA)
                .valor(valor)
                .data(LocalDateTime.now())
                .descricao("Saque em dinheiro")
                .build();
        transacoes.add(transacao);
    }

    public static class Builder {
        private String numero;
        private BigDecimal saldo;
        private List<Transacao> transacoes;

        public Builder numero(String numero) {
            this.numero = numero;
            return this;
        }

        public Builder saldo(BigDecimal saldo) {
            this.saldo = saldo;
            return this;
        }

        public Builder transacoes(List<Transacao> transacoes) {
            this.transacoes = transacoes;
            return this;
        }

        public Conta build() {
            if (numero == null || numero.isEmpty()) {
                throw new IllegalStateException("Número da conta não pode ser nulo ou vazio");
            }
            if (saldo == null) {
                throw new IllegalStateException("Saldo inicial da conta não pode ser nulo");
            }
            if (transacoes == null) {
                this.transacoes = new ArrayList<>();
            }
            return new Conta(this);
        }
    }
}
