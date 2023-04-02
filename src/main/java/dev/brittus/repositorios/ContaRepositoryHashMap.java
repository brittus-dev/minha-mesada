package dev.brittus.repositorios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import dev.brittus.entidades.Conta;

@ApplicationScoped
public class ContaRepositoryHashMap implements ContaRepository {

    private Map<String, Conta> contas;

    public ContaRepositoryHashMap() {
        this.contas = new HashMap<>();
    }

    @Override
    public void salvar(Conta conta) {
        contas.put(conta.getNumero(), conta);
    }

    @Override
    public Conta buscarPorNumero(String numero) {
        return contas.get(numero);
    }

    @Override
    public List<Conta> buscarTodas() {
        return new ArrayList<>(contas.values());
    }

    @Override
    public void removerPorNumero(String numero) {
        contas.remove(numero);
    }

    @Override
    public void removerTodasContas() {
        contas.clear();
    }
}
