package dev.brittus.recursos;

import io.quarkus.logging.Log;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import dev.brittus.entidades.Transacao;
import dev.brittus.servicos.ContaService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;

@Path("/contas")
public class ContaResource {

    @Inject
    ContaService contaService;

    @POST
    @Path("/{numero}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Criar uma nova conta bancária")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Conta criada com sucesso"),
            @APIResponse(responseCode = "400", description = "Número de conta já cadastrado"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @Tag(name = "Contas")
    public Response criarConta(@PathParam("numero") String numero, BigDecimal saldo) {
        try {
            contaService.criarConta(numero, saldo);
            return Response.status(Response.Status.CREATED).build();
        } catch (IllegalArgumentException e) {
            Log.warn("Erro ao criar conta: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity("Número de conta já cadastrado").build();
        } catch (Exception e) {
            Log.error("Erro interno do servidor: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro interno do servidor").build();
        }
    }

    @PUT
    @Path("/{numero}/depositar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Realizar um depósito em uma conta bancária")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Depósito realizado com sucesso"),
            @APIResponse(responseCode = "400", description = "Conta não encontrada"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @Tag(name = "Contas")
    public Response depositar(@PathParam("numero") String numero, BigDecimal valor) {
        try {
            contaService.depositar(numero, valor);
            return Response.ok().build();
        } catch (IllegalArgumentException e) {
            Log.warn("Erro ao depositar dinheiro: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity("Conta não encontrada").build();
        } catch (Exception e) {
            Log.error("Erro interno do servidor: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro interno do servidor").build();
        }
    }

    @PUT
    @Path("/{numero}/sacar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Realizar um saque em uma conta bancária")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Saque realizado com sucesso"),
            @APIResponse(responseCode = "400", description = "Conta não encontrada ou saldo insuficiente"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @Tag(name = "Contas")
    public Response sacar(@PathParam("numero") String numero, BigDecimal valor) {
        try {
            contaService.sacar(numero, valor);
            return Response.ok().build();
        } catch (IllegalArgumentException e) {
            Log.warn("Erro ao sacar dinheiro: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity("Conta não encontrada ou saldo insuficiente")
                    .build();
        } catch (Exception e) {
            Log.error("Erro interno do servidor: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro interno do servidor").build();
        }
    }

    @GET
    @Path("/{numero}/saldo")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Consultar o saldo de uma conta bancária")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Consulta realizada com sucesso"),
            @APIResponse(responseCode = "400", description = "Conta não encontrada"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @Tag(name = "Contas")
    public Response consultarSaldo(@PathParam("numero") String numero) {
        try {
            BigDecimal saldo = contaService.consultarSaldo(numero);
            return Response.ok(saldo).build();
        } catch (IllegalArgumentException e) {
            Log.warn("Erro ao consultar saldo: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity("Conta não encontrada").build();
        } catch (Exception e) {
            Log.error("Erro interno do servidor: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro interno do servidor").build();
        }
    }

    @GET
    @Path("/{numero}/transacoes")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Consultar as transações de uma conta bancária")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Consulta realizada com sucesso"),
            @APIResponse(responseCode = "400", description = "Conta não encontrada"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @Tag(name = "Contas")
    public Response consultarTransacoes(@PathParam("numero") String numero) {
        try {
            List<Transacao> transacoes = contaService.consultarTransacoes(numero);
            return Response.ok(transacoes).build();
        } catch (IllegalArgumentException e) {
            Log.warn("Erro ao consultar transações: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity("Conta não encontrada").build();
        } catch (Exception e) {
            Log.error("Erro interno do servidor: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro interno do servidor").build();
        }
    }

    @DELETE
    @Path("/{numero}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Excluir uma conta bancária")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Conta excluída com sucesso"),
            @APIResponse(responseCode = "400", description = "Conta não encontrada"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @Tag(name = "Contas")
    public Response excluirConta(@PathParam("numero") String numero) {
        try {
            contaService.excluirConta(numero);
            return Response.ok().build();
        } catch (IllegalArgumentException e) {
            Log.warn("Erro ao excluir conta: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity("Conta não encontrada").build();
        } catch (Exception e) {
            Log.error("Erro interno do servidor: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro interno do servidor").build();
        }
    }
}
