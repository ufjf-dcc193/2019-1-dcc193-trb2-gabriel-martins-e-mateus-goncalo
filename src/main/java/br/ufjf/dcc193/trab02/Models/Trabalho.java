package br.ufjf.dcc193.trab02.Models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Trabalho {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String titulo;
    private String descricaoTextual;
    private String url;
    
    @ManyToOne
    @JoinColumn
    private AreaDeConhecimento trabalhoAreaDeConhecimento;

    @OneToMany(mappedBy = "trabalhoRevisao", cascade = CascadeType.ALL)
    private Set<Revisao> revisoes;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AreaDeConhecimento getTrabalhoAreaDeConhecimento() {
        return trabalhoAreaDeConhecimento;
    }

    public void setTrabalhoAreaDeConhecimento(AreaDeConhecimento trabalhoAreaDeConhecimento) {
        this.trabalhoAreaDeConhecimento = trabalhoAreaDeConhecimento;
    }

    public Set<Revisao> getRevisoes() {
        return revisoes;
    }

    public void setRevisoes(Set<Revisao> revisoes) {
        this.revisoes = revisoes;
    }

}