package br.ufjf.dcc193.trab02.Models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Revisao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @OneToOne(mappedBy = "revisor", cascade = CascadeType.ALL, 
    fetch = FetchType.LAZY, optional = true)
    private Avaliador revisor;

    @OneToOne(mappedBy = "trabalhoRevisao", cascade = CascadeType.ALL, 
    fetch = FetchType.LAZY, optional = true)
    private Trabalho trabalhoRevisao;
    
    private Integer nota;
    private String descricao;
    private Integer status;

    public Revisao(Avaliador avaliador, Trabalho trabalho, Integer nota, String descricao, Integer status) {
        this.revisor = avaliador;
        this.trabalhoRevisao = trabalho;
        this.nota = nota;
        this.descricao = descricao;
        this.status = status;
    }

    public Revisao() {
    }

    public Avaliador getAvaliador() {
        return revisor;
    }

    public void setAvaliador(Avaliador avaliador) {
        this.revisor = avaliador;
    }

    public Trabalho getTrabalho() {
        return trabalhoRevisao;
    }

    public void setTrabalho(Trabalho trabalho) {
        this.trabalhoRevisao = trabalho;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
}
