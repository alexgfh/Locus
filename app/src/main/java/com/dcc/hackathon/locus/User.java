package com.dcc.hackathon.locus;

/**
 * Created by andre on 28/08/2016.
 */
public class User {



    String usuario;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    String nome;
    String senha;

    public User(String nome, String usuario, String senha)
    {
        this.nome = nome;
        this.usuario = usuario;
        this.senha = senha;

    }



}
