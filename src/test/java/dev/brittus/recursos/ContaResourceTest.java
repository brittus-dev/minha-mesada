package dev.brittus.recursos;

import dev.brittus.entidades.Transacao;
import dev.brittus.servicos.ContaService;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@QuarkusTest
public class ContaResourceTest {

    @InjectMocks
    ContaResource contaResource;

    @Mock
    ContaService contaService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCriarConta() {
        String numero = "12345";
        BigDecimal saldo = BigDecimal.valueOf(1000);

        doNothing().when(contaService).criarConta(numero, saldo);

        Response response = contaResource.criarConta(numero, saldo);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        verify(contaService).criarConta(numero, saldo);
    }

    @Test
    public void testDepositar() {
        String numero = "12345";
        BigDecimal valor = BigDecimal.valueOf(500);

        doNothing().when(contaService).depositar(numero, valor);

        Response response = contaResource.depositar(numero, valor);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(contaService).depositar(numero, valor);
    }

    @Test
    public void testSacar() {
        String numero = "12345";
        BigDecimal valor = BigDecimal.valueOf(200);

        doNothing().when(contaService).sacar(numero, valor);

        Response response = contaResource.sacar(numero, valor);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(contaService).sacar(numero, valor);
    }

    @Test
    public void testConsultarSaldo() {
        String numero = "12345";
        BigDecimal saldo = BigDecimal.valueOf(1000);

        when(contaService.consultarSaldo(numero)).thenReturn(saldo);

        Response response = contaResource.consultarSaldo(numero);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(saldo, response.getEntity());
        verify(contaService).consultarSaldo(numero);
    }

    @Test
    public void testConsultarTransacoes() {
        String numero = "12345";
        List<Transacao> transacoes = new ArrayList<>();

        when(contaService.consultarTransacoes(numero)).thenReturn(transacoes);

        Response response = contaResource.consultarTransacoes(numero);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(transacoes, response.getEntity());
        verify(contaService).consultarTransacoes(numero);
    }

    @Test
    public void testExcluirConta() {
        String numero = "12345";

        doNothing().when(contaService).excluirConta(numero);

        Response response = contaResource.excluirConta(numero);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(contaService).excluirConta(numero);
    }
}
