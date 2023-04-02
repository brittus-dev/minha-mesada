package dev.brittus.entidades;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transacao {
    private final TipoTransacao tipo;
    private final BigDecimal valor;
    private final LocalDateTime data;
    private final String descricao;

    private Transacao(Builder builder) {
        this.tipo = builder.tipo;
        this.valor = builder.valor;
        this.data = builder.data;
        this.descricao = builder.descricao;
    }

    public enum TipoTransacao {
        ENTRADA, SAIDA
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDateTime getData() {
        return data;
    }

    public String getDescricao() {
        return descricao;
    }

    public static class Builder {
        private TipoTransacao tipo;
        private BigDecimal valor;
        private LocalDateTime data;
        private String descricao;

        public Builder tipo(TipoTransacao tipo) {
            this.tipo = tipo;
            return this;
        }

        public Builder valor(BigDecimal valor) {
            this.valor = valor;
            return this;
        }

        public Builder data(LocalDateTime data) {
            this.data = data;
            return this;
        }

        public Builder descricao(String descricao) {
            this.descricao = descricao;
            return this;
        }

        public Transacao build() {
            if (tipo == null) {
                throw new IllegalStateException("Tipo da transação não pode ser nulo");
            }
            if (valor == null) {
                throw new IllegalStateException("Valor da transação não pode ser nulo");
            }
            if (data == null) {
                throw new IllegalStateException("Data da transação não pode ser nulo");
            }
            if (descricao == null) {
                throw new IllegalStateException("Descrição da transação não pode ser nulo");
            }
            return new Transacao(this);
        }
    }
}
