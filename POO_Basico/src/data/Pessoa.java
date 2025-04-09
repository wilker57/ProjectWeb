package data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import utils.Utilitario;

public class Pessoa {
    private String nome;
    private Date dataNascimento;
    private String cpf;
    private List<Telefone> telefones;
    private List<Email> emails;

    public Pessoa(String nome, Date dataNascimento, String cpf) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.telefones = new ArrayList<Telefone>();
        this.emails = new ArrayList<Email>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if(Utilitario.isCpfValido(cpf))
            this.cpf = cpf;
        else
            throw new IllegalArgumentException("CPF inválido");
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public String getTelefonesFormatados() {
        return telefones.stream()
                        .map(Telefone::toString)
                        .collect(Collectors.joining(", "));
    }

    public void adicionarTelefone(Telefone telefone) {
        telefones.add(telefone);
    }

    public void removerTelefone(Telefone telefone) {
        telefones.remove(telefone);
    }

    public List<Email> getEmails() {
        return emails;
    }

    public String getEmailsFormatados() {
        return emails.stream()
                     .map(Email::toString)
                     .collect(Collectors.joining(", "));
    }

    public void adicionarEmail(Email email) {
        emails.add(email);
    }

    public void removerEmail(Email email) {
        emails.remove(email);
    }

    @Override
    public String toString() {
        return nome + " (" + cpf + ")";
    }

    @Override
    public boolean equals(Object item) {
         if ((this.nome.equals(((Pessoa) item).getNome()) && 
             (this.dataNascimento.equals(((Pessoa) item).getDataNascimento())) &&
             (this.cpf.equals(((Pessoa) item).getCpf())))){
              return true;
         }
         return false;
    }

}

