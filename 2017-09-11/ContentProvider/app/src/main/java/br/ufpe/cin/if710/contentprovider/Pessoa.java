package br.ufpe.cin.if710.contentprovider;

public class Pessoa {

    private int _id;
    private String nome;
    private String cpf;
    private String email;
    private float media;

    public Pessoa(int i, String n, String c, String e) {
        _id = i;
        nome = n;
        cpf = c;
        email = e;
        media = 0;
    }

    public Pessoa(int i, String n, String c, String e, float m) {
        _id = i;
        nome = n;
        cpf = c;
        email = e;
        media = m;
    }

    public float getMedia() {
        return media;
    }

    public void setMedia(float media) {
        this.media = media;
    }

    public int get_id() {
        return _id;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}