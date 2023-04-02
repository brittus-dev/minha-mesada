package dev.brittus.entidade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.brittus.entidades.Conta;
import dev.brittus.servicos.ContaService;
import io.quarkus.test.junit.QuarkusTest;

import java.math.BigDecimal;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class ContaTest {

    @Inject
    private ContaService contaService;

    @BeforeEach
    void setUp() {
        contaService.limparContas();
    }

    @Test
    void testCriarConta() {
        String numero = "12345-6";
        BigDecimal saldo = new BigDecimal("1000.00");

        contaService.criarConta(numero, saldo);

        Conta conta = contaService.buscarConta(numero);
        assertNotNull(conta);
        assertEquals(numero, conta.getNumero());
        assertEquals(saldo, conta.getSaldo());
    }

    @Test
    void testDepositar() {
        String numero = "12346-6";
        BigDecimal saldoInicial = new BigDecimal("1000.00");
        BigDecimal valorDeposito = new BigDecimal("500.00");

        contaService.criarConta(numero, saldoInicial);
        contaService.depositar(numero, valorDeposito);

        Conta conta = contaService.buscarConta(numero);
        assertEquals(saldoInicial.add(valorDeposito), conta.getSaldo());
    }

    @Test
    void testSacar() {
        String numero = "12347-6";
        BigDecimal saldoInicial = new BigDecimal("1000.00");
        BigDecimal valorSaque = new BigDecimal("500.00");

        contaService.criarConta(numero, saldoInicial);
        contaService.sacar(numero, valorSaque);

        Conta conta = contaService.buscarConta(numero);
        assertEquals(saldoInicial.subtract(valorSaque), conta.getSaldo());
    }

    @Test
    void testConsultarSaldo() {
        String numero = "12348-6";
        BigDecimal saldoInicial = new BigDecimal("1000.00");

        contaService.criarConta(numero, saldoInicial);

        BigDecimal saldo = contaService.consultarSaldo(numero);
        assertEquals(saldoInicial, saldo);
    }

    @Test
    void testConsultarTransacoes() {
        String numero = "12349-6";
        BigDecimal saldoInicial = new BigDecimal("1000.00");
        BigDecimal valorDeposito = new BigDecimal("500.00");

        contaService.criarConta(numero, saldoInicial);
        contaService.depositar(numero, valorDeposito);
        contaService.depositar(numero, valorDeposito);

        Conta conta = contaService.buscarConta(numero);
        assertEquals(2, conta.getTransacoes().size());
    }

    @Test
    void testExcluirConta() {
        String numero = "12340-6";
        BigDecimal saldo = new BigDecimal("1000.00");

        contaService.criarConta(numero, saldo);
        contaService.excluirConta(numero);

        assertThrows(IllegalArgumentException.class, () -> {
            contaService.buscarConta(numero);
        });
    }

    @Test
    void testCriarContaDuplicada() {
        String numero = "123452-6";
        BigDecimal saldo = new BigDecimal("1000.00");

        contaService.criarConta(numero, saldo);

        assertThrows(IllegalArgumentException.class, () -> {
            contaService.criarConta(numero, saldo);
        });
    }

    @Test
    void testBuscarContaInexistente() {
        String numero = "123453-6";
        assertThrows(IllegalArgumentException.class, () -> {
            contaService.buscarConta(numero);
        });
    }

    @Test
    void testDepositarContaInexistente() {
        String numero = "123454-6";
        BigDecimal valorDeposito = new BigDecimal("500.00");

        assertThrows(IllegalArgumentException.class, () -> {
            contaService.depositar(numero, valorDeposito);
        });
    }

    @Test
    void testSacarContaInexistente() {
        String numero = "123455-6";
        BigDecimal valorSaque = new BigDecimal("500.00");

        assertThrows(IllegalArgumentException.class, () -> {
            contaService.sacar(numero, valorSaque);
        });
    }

    @Test
    void testExcluirContaInexistente() {
        String numero = "123456-6";

        assertThrows(IllegalArgumentException.class, () -> {
            contaService.excluirConta(numero);
        });
    }

}
