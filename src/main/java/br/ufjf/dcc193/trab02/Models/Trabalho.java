package br.ufjf.dcc193.trab02.Models;

public class Trabalho {

    private String titulo;
    private String descricaoTextual;
    private String url;

    public Trabalho(String titulo, String descricaoTextual, String url) {
        this.titulo = titulo;
        this.descricaoTextual = descricaoTextual;
        this.url = url;
    }

    public Trabalho() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricaoTextual() {
        return descricaoTextual;
    }

    public void setDescricaoTextual(String descricaoTextual) {
        this.descricaoTextual = descricaoTextual;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
}