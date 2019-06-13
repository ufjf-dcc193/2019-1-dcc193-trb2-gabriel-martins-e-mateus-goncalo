package br.ufjf.dcc193.trab02.Models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Trabalho {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String titulo;
    private String descricaoTextual;
    private String url;
    
    @OneToOne(mappedBy = "trabalho", cascade = CascadeType.ALL, 
    fetch = FetchType.LAZY, optional = true)
    private AreaDeConhecimento areaDeConhecimento;

    public Trabalho(Long id, String titulo, String descricaoTextual, String url) {
        this.id = id;
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