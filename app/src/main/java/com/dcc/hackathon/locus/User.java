package com.dcc.hackathon.locus;

/**
 * Created by andre on 28/08/2016.
 */
public class User {

    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    String nome;
    String senha;

    public User()
    {
        this.nome = nome;
        this.senha = senha;

    }



}
