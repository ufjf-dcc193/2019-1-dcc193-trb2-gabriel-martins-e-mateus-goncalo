package br.ufjf.dcc193.trab02.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Revisao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Avaliador avaliador;
    private Trabalho trabalho;
    private Integer nota;
    private String descricao;
    private Integer status;

    public Revisao(Avaliador avaliador, Trabalho trabalho, Integer nota, String descricao, Integer status) {
        this.avaliador = avaliador;
        this.trabalho = trabalho;
        this.nota = nota;
        this.descricao = descricao;
        this.status = status;
    }

    public Revisao() {
    }

    public Avaliador getAvaliador() {
        return avaliador;
    }

    public void setAvaliador(Avaliador avaliador) {
        this.avaliador = avaliador;
    }

    public Trabalho getTrabalho() {
        return trabalho;
    }

    public void setTrabalho(Trabalho trabalho) {
        this.trabalho = trabalho;
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
