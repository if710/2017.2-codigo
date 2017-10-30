package br.ufpe.cin.if710.testingespresso;

public class Pessoa {
    private String nome;
    private String login;
    //...

    public Pessoa(String nome, String login) {
        this.nome = nome;
        this.login = login;
    }

    public String getNome() {
        return nome;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public String toString() {
        return nome;
        //return nome + '(' + login + ')';
    }
}