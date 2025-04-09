package data;

import java.util.ArrayList;
import java.util.List;

public class Agenda {
    private List<Pessoa> pessoas;

    public Agenda() {
        this.pessoas = new ArrayList<>();
    }

    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    public void adicionarPessoa(Pessoa pessoa) {
        pessoas.add(pessoa);
    }

    public void removerPessoa(Pessoa pessoa) {
        pessoas.remove(pessoa);
    }
}

