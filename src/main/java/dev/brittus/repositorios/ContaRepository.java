package dev.brittus.repositorios;

import java.util.List;
import java.util.Optional;

import dev.brittus.entidades.Conta;

import java.util.List;

public interface ContaRepository {

    void salvar(Conta conta);

    void removerPorNumero(String numero);

    Conta buscarPorNumero(String numero);

    List<Conta> buscarTodas();

    void removerTodasContas();

}
