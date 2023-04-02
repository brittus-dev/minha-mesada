package dev.brittus.servicos;

import javax.inject.Inject;
import javax.inject.Singleton;

import dev.brittus.entidades.Conta;
import dev.brittus.entidades.Transacao;
import dev.brittus.repositorios.ContaRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class ContaService {

    @Inject
    ContaRepository contaRepository;

    public ContaService() {

    }

    public ContaService(ContaRepository contaRepository2) {
    }

    public void criarConta(String numero, BigDecimal saldo) {
        Conta contaExistente = contaRepository.buscarPorNumero(numero);
        if (contaExistente != null) {
            throw new IllegalArgumentException("Número de conta já cadastrado");
        }
        Conta conta = new Conta.Builder()
                .numero(numero)
                .saldo(saldo)
                .build();
        contaRepository.salvar(conta);
    }

    public void depositar(String numero, BigDecimal valor) {
        Conta conta = buscarConta(numero);
        conta.depositar(valor);
        contaRepository.salvar(conta);
    }

    public void sacar(String numero, BigDecimal valor) {
        Conta conta = buscarConta(numero);
        conta.sacar(valor);
        contaRepository.salvar(conta);
    }

    public BigDecimal consultarSaldo(String numero) {
        Conta conta = buscarConta(numero);
        return conta.getSaldo();
    }

    public List<Transacao> consultarTransacoes(String numero) {
        Conta conta = buscarConta(numero);
        return conta.getTransacoes();
    }

    public void excluirConta(String numero) {
        Conta conta = buscarConta(numero);
        contaRepository.removerPorNumero(numero);
    }

    public Conta buscarConta(String numero) {
        Conta conta = contaRepository.buscarPorNumero(numero);
        if (conta == null) {
            throw new IllegalArgumentException("Conta não encontrada");
        }
        return conta;
    }

    public void limparContas() {
        contaRepository.removerTodasContas();
    }
}
